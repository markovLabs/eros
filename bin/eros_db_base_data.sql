USE eros;
INSERT INTO `DATING_MAPPING` (`MAPPING`)
VALUES
('{"R1":[{"mapping":"M1:F1","story_id":"U”},{"mapping":"M2:F2","story_id":"U"},{"mapping":"M3:F3","story_id":"PA"},{"mapping":"M4:F4","story_id":"PA"}],"R2":[{"mapping":"M1:F2","story_id":"PA"},{"mapping":"M2:F1","story_id":"PA"},{"mapping":"M3:F4","story_id":"U"},{"mapping":"M4:F3","story_id":"U"}],"R3":[{"mapping":"M1:F3","story_id":"U"},{"mapping":"M2:F4","story_id":"U"},{"mapping":"M3:F1","story_id":"PB"},{"mapping":"M4:F2","story_id":"PB"}],"R4":[{"mapping":"M1:F4","story_id":"PB"},{"mapping":"M2:F3","story_id":"PB"},{"mapping":"M3:F2","story_id":"U"},{"mapping":"M4:F1","story_id":"U"}]}'),
('{"R1":[{"mapping":"M1:F1","story_id":"U”},{"mapping":"M2:F2","story_id":"U"},{"mapping":"M3:F3","story_id":"PA"},{"mapping":"M4:F4","story_id":"PA"},{"mapping":"M5:F5","story_id":"U"
},{"mapping":"M6:F6","story_id":"U"}],"R2":[{"mapping":"M1:F2","story_id":"PA"},{"mapping":"M2:F1","story_id":"PA"},{"mapping":"M3:F4","story_id":"U"},{"mapping":"M4:F3","story_id":"U"},{"mapping":"M5:F6","story_id":"PA"},{"mapping":"M6:F5","story_id":"PA"}],"R3":[{"mapping":"M1:F5","story_id":"U"},{"mapping":"M2:F6","story_id":"U"},{"mapping":"M3:F1","story_id":"PB"},{"mapping":"M4:F2","story_id":"PB"},{"mapping":"M5:F3","story_id":"U"},{"mapping":"M6:F4","story_id":"U"}],"R4":[{"mapping":"M1:F6","story_id":"PB"},{"mapping":"M2:F5","story_id":"PB"},{"mapping":"M3:F2","story_id":"U"},{"mapping":"M4:F1","story_id":"U”},{"mapping":"M5:F4","story_id":"PB”},{"mapping":"M6:F3","story_id":"PB"}],"R5":[{"mapping":"M1:F3","story_id":"U"},{"mapping":"M2:F4","story_id":"U"},{"mapping":"M3:F5","story_id":"PC"},{"mapping":"M4:F6","story_id":"PC”},{"mapping":"M5:F1","story_id":"U”},{"mapping":"M6:F2","story_id":"U”}],"R6":[{"mapping":"M1:F4","story_id":"PC"},{"mapping":"M2:F3","story_id":"PC"},{"mapping":"M3:F6","story_id":"U"},{"mapping":"M4:F5","story_id":"U”},{"mapping":"M5:F2","story_id":"PC”},{"mapping":"M6:F1","story_id":"PC”}]}'),
('{"R1":[{"mapping":"M1:F1","story_id":"U”},{"mapping":"M2:F2","story_id":"U"},{"mapping":"M3:F3","story_id":"U"},{"mapping":"M4:F4","story_id":"U"},{"mapping":"M5:F5","story_id":"PA"},{"mapping":"M6:F6","story_id":"PA"},{"mapping":"M7:F7","story_id":"PA"},{"mapping":"M8:F8","story_id":"PA"}],"R2":[{"mapping":"M1:F4","story_id":"PB"},{"mapping":"M2:F1","story_id":"PB"},{"mapping":"M3:F2","story_id":"PB"},{"mapping":"M4:F3","story_id":"PB"},{"mapping":"M5:F8","story_id":"U"},{"mapping":"M6:F5","story_id":"U"},{"mapping":"M7:F6","story_id":"U"},{"mapping":"M8:F7","story_id":"U"}],"R3":[{"mapping":"M1:F3","story_id":"U"},{"mapping":"M2:F4","story_id":"U"},{"mapping":"M3:F1","story_id":"U"},{"mapping":"M4:F2","story_id":"U"},{"mapping":"M5:F7","story_id":"PB"},{"mapping":"M6:F8","story_id":"PB"},{"mapping":"M7:F5","story_id":"PB"},{"mapping":"M8:F6","story_id":"PB"}],"R4":[{"mapping":"M1:F2","story_id":"PA"},{"mapping":"M2:F3","story_id":"PA"},{"mapping":"M3:F4","story_id":"PA"},{"mapping":"M4:F1","story_id":"PA”},{"mapping":"M5:F6","story_id":"U”},{"mapping":"M6:F7","story_id":"U"},{"mapping":"M7:F8","story_id":"U"},{"mapping":"M8:F5","story_id":"U"}],"R5":[{"mapping":"M1:F5","story_id":"U"},{"mapping":"M2:F6","story_id":"U"},{"mapping":"M3:F7","story_id":"U"},{"mapping":"M4:F8","story_id":"U”},{"mapping":"M5:F1","story_id":"PC”},{"mapping":"M6:F2","story_id":"PC”},{"mapping":"M7:F3","story_id":"PC"},{"mapping":"M8:F4","story_id":"PC"}],"R6":[{"mapping":"M1:F8","story_id":"PD"},{"mapping":"M2:F5","story_id":"PD"},{"mapping":"M3:F6","story_id":"PD"},{"mapping":"M4:F7","story_id":"PD”},{"mapping":"M5:F4","story_id":"U”},{"mapping":"M6:F1","story_id":"U”},{"mapping":"M7:F2","story_id":"U"},{"mapping":"M8:F3","story_id":"U"}],"R7":[{"mapping":"M1:F7","story_id":"U"},{"mapping":"M2:F8","story_id":"U"},{"mapping":"M3:F5","story_id":"U"},{"mapping":"M4:F6","story_id":"U”},{"mapping":"M5:F3","story_id":"PD”},{"mapping":"M6:F4","story_id":"PD”},{"mapping":"M7:F1","story_id":"PD"},{"mapping":"M8:F2","story_id":"PD"}],"R8":[{"mapping":"M1:F6","story_id":"PC"},{"mapping":"M2:F7","story_id":"PC"},{"mapping":"M3:F8","story_id":"PC"},{"mapping":"M4:F5","story_id":"PC”},{"mapping":"M5:F2","story_id":"U”},{"mapping":"M6:F3","story_id":"U”},{"mapping":"M7:F4","story_id":"U"},{"mapping":"M8:F1","story_id":"U"}]}');

INSERT INTO `STORY` (`CONTENT`, `QUESTION`, `ANSWER_A`, `ANSWER_B`, `STORY_TYPE`)
VALUES
("Use the messaging interface to discuss the following story and your opinions. Try to confirm or settle on one opinion choice that you can both agree on. \n Jack and Stephanie are on their first date. They are having a good time together and the hours pass by quickly. It’s getting late and they both need to work tomorrow. They agree they want to go on a second date. Jack suggests this coming Thursday. Stephanie says 'sorry I can't. I have a date scheduled with someone else that day.'' Jack is bothered by this.", "Is his reaction to Stephanie’s response justified?", "Yes, Jack should be bothered", "No, Jack should not bed bothered", "Prompted"),
("Use the messaging interface to discuss the following story and your opinions. Try to confirm or settle on one opinion choice that you can both agree on. \n Annabelle and Donald are at a bar/restaurant for their first date. They are having an easy-flowing conversation and they discovered that they have a lot in common. They both had one beer so far. Donald gets up to order more drinks at the bar and says, 'the next one is on me.' He returns with a beer for Annabelle and a Sprite for himself. Annabelle is bothered by this.", "Is her reaction to Donald’s behavior justified?", "Yes, Annabelle should be bothered by Donald returning with a beer for her and a Sprite for him", "No, Annabelle should not be bothered by Donald returning with a beer for her and a Sprite for him", "Prompted"),
("Use the messaging interface to discuss the following story and your opinions. Try to confirm or settle on one opinion choice that you can both agree on. \n Brian and Nancy are at a bar/restaurant for their first date. They are having an easy-flowing conversation and they discovered that they have a lot in common. While Nancy is telling a funny story about her job she hears a beep and takes her phone out of her purse to check a new text message she just received. Brian is bothered by this.", "Is his reaction to Nancy's behavior justified?", "Yes, Brian should be bothered by Nancy checking her new message", "No, Brian should not be bothered by Nancy checking her new message", "Prompted"),
("Use the messaging interface to discuss the following story and your opinions. Try to confirm or settle on one opinion choice that you can both agree on. \n Frank and Lisa are on their first date and they are having a good time together. Near the end of the date they are discussing recent political topics including gay marriage when Lisa asks Frank, 'have you ever had sex with another man?' Frank thinks about how he had a couple one-night stands with men in his younger adult years. He decides to answer no.", "Was it okay for Frank to lie in this situation?", "Yes, it was okay for Frank to lie about his homosexual experiences", "No, it was not okay for Frank to lie about his homosexual experiences", "Prompted"),
("Use the messaging interface to discuss the following story and your opinions. Try to confirm or settle on one opinion choice that you can both agree on. \n Harry and Danielle just finished their first date. They had an easy-flowing conversation and discovered they have a lot of things in common. However, Harry learned that Danielle is friends with his ex-girlfriend who he’s no longer on speaking terms with. This is a deal breaker for Harry—he decides not to go on any more dates with Danielle because she’s friends with his ex.","Was this a good reason for Harry to reject Danielle?", "Yes, Danielle being friends with his ex was a good reason for Harry to reject Danielle", "No, Danielle being friends with his ex was not a good reason for Harry to reject Danielle", "Prompted"),
("Use the messaging interface to discuss the following story and your opinions. Try to confirm or settle on one opinion choice that you can both agree on. \n Tony and Joan just finished their first date. They had an easy-flowing conversation and discovered they have a lot in common. However, Joan, who is 5'3'', learned that Tony is actually 5'10''—he had told her before the date that he was 6'1''. This is a deal breaker for Joan—she decides to not go on any more dates with Tony because he lied about his height.","Was this a good reason for Joan to reject Tony?", "Yes, Tony’s dishonesty about his height was a good reason for Joan to reject Tony", "No, Tony’s dishonesty about his height was not a good reason for Joan to reject Tony", "Prompted"),
("Use the messaging interface to discuss the following story and your opinions. Try to confirm or settle on one opinion choice that you can both agree on. \n Julia and Mark met on a dating app and they are going on their first date tonight. A few hours before their date, Mark sends Julia a selfie in the mirror with his date outfit. He asks 'what do you think?' The picture makes Julia realize that she isn't physically attracted to Mark at all. This is a deal breaker for Julia, and she decides that she's going to cancel the date.","Did she make the right decision?", "Yes, Julia made the right decision", "No, Julia did not make the right decision", "Prompted"),
("Use the messaging interface to discuss the following story and your opinions. Try to confirm or settle on one opinion choice that you can both agree on. \n Victor and Courtney had their first date a few nights ago. Courtney didn’t feel a connection and she doesn’t want to continue dating Victor. Today they are talking and Courtney plans to tell Victor that she doesn't want to go on a second date. She says, ‘I’m sorry Victor, but you’re not my type physically. I think it’s best we stop seeing each other.", "Was this a good way for Courtney to reject Victor?", "Yes, this was a good way for Courtney to reject Victor", "No, this was not a good way for Courtney to reject Victor", "Prompted"),
("Use the messaging interface to discuss the following story and your opinions. Try to confirm or settle on one opinion choice that you can both agree on. \n Feel free to discuss whatever you'd like, but please do not discuss this dating event or conversations you had with other daters.","", "", "", "Unprompted");

INSERT INTO `QUESTION` (`CONTENT`,`PAGE_TYPE`)
VALUES
("Type your first and last name (this won’t be visible to other daters).", "Consent Page"),
("I agree to participate in this study.", "Consent Page"),
("Select your gender and sexual preference.", "Acceptance Page"),
("Are you currently single (i.e. not a committed relationship with another person)?", "Acceptance Page"),
("What is your age? (Please be honest; other daters will not see this)", "Acceptance Page"),
("What is your first name? (Other daters will see this)", "Profile Creation"),
("Which dating event are you signing up for?", "Profile Creation"),
("How many total months have you been a user of at least one online dating system? (Online dating systems include mobile dating apps and online dating websites).", "Profile Creation"),
("What is your highest level of education completed?", "Profile Creation"),
("What is your ethnicity?", "Profile Creation"),
("Are you Hispanic/Latino?", "Profile Creation"),
("If I had to make a choice right now, I would choose to go on a date with this person.", "Profile Evaluation"),
("I am very confident in my answer to the previous question.", "Profile Evaluation"),
("Regardless of your initial opinion choices, did the conversation you just had end with an agreement of opinion choice?", "Messaging Evaluation"),
("If I had to make a choice right now, I would choose to go on a date with this person.", "Messaging Evaluation"),
("I am very confident in my answer to the previous question.", "Messaging Evaluation"),
("I was very engaged and interested in the discussion I had with this person in the messaging interface.", "Messaging Evaluation"),
("How would you rate the overall ''impact'' that this person’s statements had on you during your messaging interaction?", "Messaging Evaluation"),
("Quality of the messaging interaction (How pleasant was it?)", "Messaging Evaluation"),
("Degree of disagreement/conflict throughout the entire messaging interaction", "Messaging Evaluation"),
("Degree of closeness/camaraderie during the messaging interaction", "Messaging Evaluation"),
("My level of satisfaction with the messaging interaction", "Messaging Evaluation"),
("I got from this messaging interaction…", "Messaging Evaluation");

