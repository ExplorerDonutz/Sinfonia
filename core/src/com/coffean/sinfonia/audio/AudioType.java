package com.coffean.sinfonia.audio;

public enum AudioType {
    BOSS("audio/boss.ogg", true, 0.5f);
    private final String filePath;
    private final boolean isMusic;

    AudioType(final String filePath, final boolean isMusic, final float volume) {
        this.filePath = filePath;
        this.isMusic = isMusic;
    }

    public String getFilePath() {
        return filePath;
    }

    public Boolean isMusic() {
        return isMusic;
    }
}
