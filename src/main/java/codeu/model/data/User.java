// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.data;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import com.google.appengine.api.images.Image;

/** Class representing a registered user. */
public class User {
  private final UUID id;
  private final String name;
  private final String password;
  private String about; 
  private boolean allowMessageDel;
  private int messagesSent; 
  private final Instant creation;
  private boolean showAllConversations;
  private boolean isAdmin;
  private byte[] profilePictureBytes;
  private Map<UUID, Boolean> conversationVisibilities;


  /**
   * Constructs a new User.
   *
   * @param id the ID of this User
   * @param name the username of this User
   * @param password the password of this User
   * @param about the about me message of this User 
   * @param allowMesssageDel does this User want messages deleted?
   * @param messagesSent number of messages this user sent 
   * @param creation the creation time of this User
   * @param showAllConversations whether the user wants to display all their conversations
   * @param isAdmin the isAdmin value of this User
   * @param profilePicture the profile picture data of this User 
   * @param conversations the map that shows which conversations the user wants to hide
   *
   */
  public User(UUID id, String name, String password, String about, boolean allowMessageDel, 
              int messagesSent, Instant creation, boolean showAllConversations, 
              boolean isAdmin, byte[] profilePicture, Map conversations) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.about = about;
    this.allowMessageDel = allowMessageDel;
    this.messagesSent = messagesSent;
    this.creation = creation;
    this.showAllConversations = showAllConversations;
    this.isAdmin = isAdmin;
    this.profilePictureBytes = profilePicture;
    this.conversationVisibilities = conversations;
  }

  /**
   * Constructs a new User.
   *
   * @param id the ID of this User
   * @param name the username of this User
   * @param password the password of this User
   * @param creation the creation time of this User
   * @param isAdmin the isAdmin value of this User
   *
   */
   public User(UUID id, String name, String password, Instant creation, boolean isAdmin) {

    this.id = id;
    this.name = name;
    this.password = password;
    this.about = "Hi! I'm " + name + "!";
    this.allowMessageDel = true;
    this.messagesSent = 0; 
    this.creation = creation;
    this.showAllConversations = false;
    this.isAdmin = isAdmin;
    this.profilePictureBytes = new byte[0];
    this.conversationVisibilities = new HashMap();
  }

  /** Returns the ID of this User. */
  public UUID getId() {
    return id;
  }

  /** Returns the username of this User. */
  public String getName() {
    return name;
  }
  
  /** Returns the password of this User */
  public String getPassword() {
    return password;
  }

  /** Returns the creation time of this User. */
  public Instant getCreationTime() {
    return creation;
  }

  /** Returns whether User is admin or not. */
  public boolean getIsAdmin() {
    return isAdmin;
  }
  /** Changes admin status of User */
  public void invertAdminStatus() {
    this.isAdmin = !(this.isAdmin);
  }

  /** Returns the "about me" message of this User. */
  public String getAbout() {
    return about;
  }

  /** Changes the "about me" message of this User */
  public void setAbout(String aboutMessage) {
    this.about = aboutMessage;
  }

  /** Returns whether this User allows message deletion */
  public boolean getAllowMessageDel() {
    return allowMessageDel;
  }

  /** Sets whether this User allows message deletion */
  public void setAllowMessageDel(Boolean delete) {
    this.allowMessageDel = delete;
  }

  /** Gets messages sent by this User */
  public int getMessagesSent() {
    return this.messagesSent;
  }

  /** Sets messages sent by this User */
  public void incMessagesSent() {
    this.messagesSent++;
  }

  /** Returns the conversation in which the user has sent a message. */
  public Map<UUID, Boolean> getConversations() {
    return conversationVisibilities;
  }

  /** Returns the profile picture of this User. */
  public byte[] getImageData() {
    return profilePictureBytes;
  }

  /** Returns the profile picture of this User as a base64 string. */
  public String getEncodedImage() {
    String base64String = Base64.getEncoder().encodeToString(profilePictureBytes);
    return base64String;
  }

  /** Changes the profile picture of this User, taking a byte array as input */
  public void setImageData(byte[] imageBytes) {
    this.profilePictureBytes = imageBytes;
  }

  /** Adds a conversation to the list */
  public void addConversation(UUID conversationId) {
    this.conversationVisibilities.put(conversationId, true);
  }

  /** Sets conversation value to false (will be private). */
  public void hideConversation(UUID conversationId) {
    this.conversationVisibilities.computeIfPresent(conversationId, (k, v) -> false);
  }

  /** Profile page will ignore the values in conversationVisibilities and treat them as true if 
   *  showAllConversations is set to true
   */
  public void showAllConversations(boolean showAllConversations) {
    this.showAllConversations = showAllConversations;
  }

  /** Returns whether the user wants to show all of their conversations. */
  public boolean getShowAllConversations() {
    return showAllConversations;
  }
}
