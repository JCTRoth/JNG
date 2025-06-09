package jng.statistics;

/**
 * This class collects and stores game statistics during a game.
 * It uses the singleton pattern to ensure that there is only one
 * instance of the statistics in the game.
 */
public class GameStatistics {

    private static GameStatistics instance;

    // General game statistics
    private int playTime; // Game time in seconds
    private int score; // Total score

    // Combat statistics
    private int shotsFired; // Shots fired
    private int shotsHit; // Shots hit
    private int enemiesDestroyed; // Enemies destroyed
    private int structuresDestroyed; // Structures destroyed

    // Player statistics
    private int itemsCollected; // Items collected
    private int damageTaken; // Damage taken
    private int distanceTraveled; // Distance traveled

    // Weapon type statistics
    private int mgShotsFired;
    private int rocketsFired;
    private int bombsDropped;

    /**
     * Private constructor for the singleton pattern
     */
    private GameStatistics() {
        // resetStatistics();
    }

    /**
     * Returns the only instance of this class
     * @return The GameStatistics instance
     */
    public static GameStatistics getInstance() {
        if (instance == null) {
            instance = new GameStatistics();
        }
        return instance;
    }

    /**
     * Resets all statistics to their default values
     */
    public void resetStatistics() {
        playTime = 0;
        score = 0;
        shotsFired = 0;
        shotsHit = 0;
        enemiesDestroyed = 0;
        structuresDestroyed = 0;
        itemsCollected = 0;
        damageTaken = 0;
        distanceTraveled = 0;
        mgShotsFired = 0;
        rocketsFired = 0;
        bombsDropped = 0;
    }

    // Getters and setters for all statistics

    public int getPlayTime() {
        return playTime;
    }

    public void incrementPlayTime() {
        this.playTime++;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getShotsFired() {
        return shotsFired;
    }

    public void incrementShotsFired() {
        this.shotsFired++;
    }

    public void setShotsFired(int shotsFired) {
        this.shotsFired = shotsFired;
    }

    public int getShotsHit() {
        return shotsHit;
    }

    public void incrementShotsHit() {
        this.shotsHit++;
    }

    public void setShotsHit(int shotsHit) {
        this.shotsHit = shotsHit;
    }

    public int getEnemiesDestroyed() {
        return enemiesDestroyed;
    }

    public void incrementEnemiesDestroyed() {
        this.enemiesDestroyed++;
    }

    public void setEnemiesDestroyed(int enemiesDestroyed) {
        this.enemiesDestroyed = enemiesDestroyed;
    }

    public int getStructuresDestroyed() {
        return structuresDestroyed;
    }

    public void incrementStructuresDestroyed() {
        this.structuresDestroyed++;
    }

    public void setStructuresDestroyed(int structuresDestroyed) {
        this.structuresDestroyed = structuresDestroyed;
    }

    public int getItemsCollected() {
        return itemsCollected;
    }

    public void incrementItemsCollected() {
        this.itemsCollected++;
    }

    public void setItemsCollected(int itemsCollected) {
        this.itemsCollected = itemsCollected;
    }

    public int getDamageTaken() {
        return damageTaken;
    }

    public void addDamageTaken(int damage) {
        this.damageTaken += damage;
    }

    public void setDamageTaken(int damageTaken) {
        this.damageTaken = damageTaken;
    }

    public int getDistanceTraveled() {
        return distanceTraveled;
    }

    public void addDistanceTraveled(int distance) {
        this.distanceTraveled += distance;
    }

    public void setDistanceTraveled(int distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    public int getMgShotsFired() {
        return mgShotsFired;
    }

    public void incrementMgShotsFired() {
        this.mgShotsFired++;
    }

    public void setMgShotsFired(int mgShotsFired) {
        this.mgShotsFired = mgShotsFired;
    }

    public int getRocketsFired() {
        return rocketsFired;
    }

    public void incrementRocketsFired() {
        this.rocketsFired++;
    }

    public void setRocketsFired(int rocketsFired) {
        this.rocketsFired = rocketsFired;
    }

    public int getBombsDropped() {
        return bombsDropped;
    }

    public void incrementBombsDropped() {
        this.bombsDropped++;
    }

    public void setBombsDropped(int bombsDropped) {
        this.bombsDropped = bombsDropped;
    }

    /**
     * Calculates the accuracy as a percentage
     * @return Accuracy in percent or 0 if no shots were fired
     */
    public int getAccuracy() {
        if (shotsFired == 0) {
            return 0;
        }
        return (int)((float)shotsHit / shotsFired * 100);
    }

    /**
     * Formats the play time in minutes and seconds
     * @return Formatted play time as a string (MM:SS)
     */
    public String getFormattedPlayTime() {
        int minutes = playTime / 60;
        int seconds = playTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
