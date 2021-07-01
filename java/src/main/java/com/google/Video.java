package com.google;

import java.util.*;

/** A class used to represent a video. */
class Video implements Comparable<Video> {

  /** 
   * ------------
   *  ATTRIBUTES
   * ------------
   */

  private final String title;
  private final String videoId;
  private final List<String> tags;

  private String flagReason = "Not supplied";

  private boolean paused;
  private boolean flagged;

  /** 
   * -------------
   *  CONSTRUCTOR
   * -------------
   */

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
    this.paused = false;
  }

  /** 
   * ---------
   *  METHODS
   * ---------
   */

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {
    return tags;
  }

   /** Returns the collection of a tags as a formatted String. */
   String getTagsString() {
    return Arrays.toString(this.getTags().toArray()).replaceAll(",", "");
  }

  public void pause() {
    this.paused = true;
  }
  
  public void resume() {
    this.paused = false;
  }

  public boolean flag(String reason) {

    if (!flagged) {

      flagged = true;
      flagReason = reason;

    } else {

      return false;

    }
    
    return true;

  }

  public boolean unflag() {

    if (flagged) {

      flagged = false;
      flagReason = "Not supplied";

    } else {

      return false;

    }
    
    return true;

  }

  public String getFlagReason() {
      return flagReason;
  }

  public boolean isFlagged() {
      return flagged;
  }

  /** 
   * @return boolean
   */
  public boolean isPaused() {
      return paused;
  }

  /** 
   * @return String
   */
  @Override
  public String toString() {

    if (this.isFlagged()) { // Check that the video is flagged

      return String.format("%s (%s) %s - FLAGGED (reason: %s)", 
        this.getTitle(), 
        this.getVideoId(),
        this.getTagsString(),
        this.getFlagReason()
      );

    }
    
    if (this.isPaused()) { // Check that the video is paused

      return String.format("%s (%s) %s - PAUSED", 
        this.getTitle(), 
        this.getVideoId(),
        this.getTagsString()
      );

    } else {

      return String.format("%s (%s) %s", 
        this.getTitle(), 
        this.getVideoId(),
        this.getTagsString()
      );

    }
    
  }

  
  /** 
   * @param v
   * @return int
   */
  @Override
  public int compareTo(Video v) {
    int lastCmp = this.getTitle().compareTo(v.getTitle());
    return (lastCmp != 0 ? lastCmp : this.getVideoId().compareTo(v.getVideoId()));
  }

}
