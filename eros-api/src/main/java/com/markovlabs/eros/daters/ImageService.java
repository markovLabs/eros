package com.markovlabs.eros.daters;

import static com.markovlabs.eros.JOOQRecordUtility.addRecord;
import static com.markovlabs.eros.model.tables.Image.IMAGE;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;

import javaslang.control.Try;

public final class ImageService {
	
	private static final Logger log = LoggerFactory.getLogger(ImageService.class);
	
	private final DSLContext erosDb;
	private final String imageQueue;

	public ImageService(DSLContext erosDb, String imageQueue) {
		this.erosDb = erosDb;
		this.imageQueue = imageQueue;
	}

	public List<Image> getImagesWithDaterId(long daterId) {
		return erosDb.selectFrom(IMAGE).where(IMAGE.DATER_ID.eq(daterId)).fetch().map(Image::of);
	}

	public Image getImageWithIdAndDaterId(long imageId, long daterId) {
		return Try.of(() -> erosDb.selectFrom(IMAGE)
					.where(IMAGE.ID.eq(imageId)
						.and(IMAGE.DATER_ID.eq(daterId)))
					.fetchOne())
				.map(Image::of)
				.getOrElseThrow(ImageNotFoundException::new);
	}

	public Image addImage(Image image) {
		List<String> originalImgNameParts = Splitter.on(".").splitToList(image.getName());
		String extension = originalImgNameParts.get(originalImgNameParts.size() - 1);
		String imageName = "img" + (int) (1000000 * Math.random()) + "." + extension;
		image.setName(imageName);
		byte[] imgContent = getImageContent(image.getContent(), extension);
		Path imagePath = Try.of(() -> Files.write(Paths.get(imageQueue + "/" + imageName), imgContent))
			.getOrElseThrow(ImageAccessException::new);
		log.info("Image for dater " + image.getDaterId() + " stored at " + imagePath.toString());
		return addRecord(erosDb, IMAGE, image);
	}

	private byte[] getImageContent(String content, String extension) {
		String prefix = "data:image/" + extension + ";base64,";
		if(extension.equals("jpg")) {
			prefix = "data:image/jpeg;base64,";
		}
		return Base64.getDecoder().decode(content.substring(prefix.length()));
	}

	public void removeImageWithDaterId(long imageId, long daterId) {
		String imageName = imageQueue + "/" + getImageWithIdAndDaterId(imageId, daterId).getName();
		Try.of(() -> Files.deleteIfExists(Paths.get(imageName)))
			.getOrElseThrow(ImageAccessException::new);
		erosDb.deleteFrom(IMAGE).where(IMAGE.ID.eq(imageId).and(IMAGE.DATER_ID.eq(daterId))).execute();
	}
	
	public static class ImageNotFoundException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		public ImageNotFoundException(Throwable e){
			super(e);
		}
	}
	
	public static class ImageAccessException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		public ImageAccessException(Throwable e){
			super(e);
		}
	}

}
