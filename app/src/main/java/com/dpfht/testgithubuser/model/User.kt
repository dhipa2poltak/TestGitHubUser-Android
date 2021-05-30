package com.dpfht.testgithubuser.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class User(

  val login: String? = null,
  val id: Long = -1L,

  @SerializedName("node_id")
  @Expose
  val nodeId: String? = null,

  @SerializedName("avatar_url")
  @Expose
  val avatarUrl: String? = null,

  @SerializedName("gravatar_id")
  @Expose
  val gravatarId: String? = null,

  val url: String? = null,

  @SerializedName("html_url")
  @Expose
  val htmlUrl: String? = null,

  @SerializedName("followers_url")
  @Expose
  val followersUrl: String? = null,

  @SerializedName("subscriptions_url")
  @Expose
  val subscriptionsUrl: String? = null,

  @SerializedName("organizations_url")
  @Expose
  val organizationsUrl: String? = null,

  @SerializedName("repos_url")
  @Expose
  val reposUrl: String? = null,

  @SerializedName("received_events_url")
  @Expose
  val receivedEventsUrl: String? = null,

  val type: String? = null,
  val score: Int = -1,

  @SerializedName("following_url")
  @Expose
  val followingUrl: String? = null,

  @SerializedName("gists_url")
  @Expose
  val gistsUrl: String? = null,

  @SerializedName("starred_url")
  @Expose
  val starredUrl: String? = null,

  @SerializedName("events_url")
  @Expose
  val eventsUrl: String? = null,

  @SerializedName("site_admin")
  @Expose
  val siteAdmin: Boolean = false
)
