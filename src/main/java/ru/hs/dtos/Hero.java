package ru.hs.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hero {
    private int id;
    private int collectible;
    private String slug;
    private int health;
    private String name;
    private String text;
    private String image;
    private String imageGold;
    private String flavorText;
    private String cropImage;
}
/*{
    "id": 7,
    "collectible": 1,
    "slug": "7-garrosh-hellscream",
    "classId": 10,
    "multiClassIds": [],
    "cardTypeId": 3,
    "cardSetId": 17,
    "rarityId": 2,
    "artistName": null,
    "health": 30,
    "manaCost": 0,
    "name": "Гаррош Адский Крик",
    "text": "",
    "image": "https://d15f34w2p8l1cc.cloudfront.net/hearthstone/e0cdacbabb27563ef90e0d843ed93d970bed8e92d87391cfdea9629243203751.png",
    "imageGold": "",
    "flavorText": "",
    "cropImage": "https://d15f34w2p8l1cc.cloudfront.net/hearthstone/118391a73894b1d576c4b0faf8f1d99237cde3047d5d8728a77f1bccfd559e92.png",
    "childIds": [
      725,
      2828,
      2830,
      57751,
      58787,
      58799,
      61595,
      61923,
      61924,
      64739,
      66836,
      67521,
      71070,
      71071,
      71072,
      71073
    ]
  }*/
