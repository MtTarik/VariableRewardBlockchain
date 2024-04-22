package org.example;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BitcoinMinerSimulation {

  public static void main(String[] args) {
    int difficulty = 1;
    long startTime = System.currentTimeMillis();

    System.out.println("Mining blocks...");
    int initialReward = 10;
    int totalCoins = 0;
    int maxCoins = 100;

    for (int blocksMined = 0; totalCoins < maxCoins; blocksMined++) {
      String blockData = "Block data" + blocksMined;
      String hash = sha256(blockData);

      System.out.println("Nonce: " + blocksMined + ", Hash: " + hash);
      if (hash.substring(0, difficulty).equals(getTarget(difficulty))) {
        System.out.println("Block mined! Nonce: " + blocksMined + ", Hash: " + hash);
        int blockReward = initialReward - blocksMined;
        if (totalCoins + blockReward > maxCoins) {
          blockReward = maxCoins - totalCoins; 
        }
        if (blockReward <= 0) {
          System.out.println("Reward exhausted!");
          break;
        }
        totalCoins += blockReward;
        System.out.println("Block reward: " + blockReward + " coins");
        System.out.println("Total coins mined: " + totalCoins);
      }
    }

    long endTime = System.currentTimeMillis();
    long totalTime = endTime - startTime;
    System.out.println("Mining took " + totalTime + " milliseconds.");
    System.out.println("Total coins mined: " + totalCoins);
  }

  public static String sha256(String data) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(data.getBytes());

      StringBuilder hexString = new StringBuilder();
      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String getTarget(int difficulty) {
    return new String(new char[difficulty]).replace('\0', '0');
  }
}
