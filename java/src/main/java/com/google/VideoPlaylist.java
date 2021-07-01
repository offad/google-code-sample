package com.google;

import java.util.*;

/** A class used to represent a Playlist */
class VideoPlaylist {

    private final String name;
    private final LinkedHashSet<Video> videos;

    VideoPlaylist(String name) {

        this.name = name;
        videos = new LinkedHashSet<Video>();

    }

    
    /** 
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @return Set<Video>
     */
    public Set<Video> getVideos() {
        return videos;
    }

    
    /** 
     * @param video
     * @return boolean
     */
    public boolean addVideo(Video video) {
        return videos.add(video);
    }

    
    /** 
     * @param video
     * @return boolean
     */
    public boolean removeVideo(Video video) {
        return videos.remove(video);
    }

    public void clear() {
        videos.clear();
    }

}
