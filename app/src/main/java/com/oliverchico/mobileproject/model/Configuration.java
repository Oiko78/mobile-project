package com.oliverchico.mobileproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Configuration {

    @SerializedName("images")
    @Expose
    private Image image;

    @SerializedName("change_keys")
    @Expose
    private List<String> changeKeys;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<String> getChangeKeys() {
        return changeKeys;
    }

    public void setChangeKeys(List<String> changeKeys) {
        this.changeKeys = changeKeys;
    }

    public class Image {

        @SerializedName("base_url")
        @Expose
        private String baseUrl;

        @SerializedName("secure_base_url")
        @Expose
        private String secureBaseUrl;

        @SerializedName("backdrop_sizes")
        @Expose
        private List<String> backdropSizes;

        @SerializedName("logo_sizes")
        @Expose
        private List<String> logoSizes;

        @SerializedName("poster_sizes")
        @Expose
        private List<String> posterSizes;

        @SerializedName("profile_sizes")
        @Expose
        private List<String> profileSizes;

        @SerializedName("still_sizes")
        @Expose
        private List<String> stillSizes;

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getSecureBaseUrl() {
            return secureBaseUrl;
        }

        public void setSecureBaseUrl(String secureBaseUrl) {
            this.secureBaseUrl = secureBaseUrl;
        }

        public List<String> getBackdropSizes() {
            return backdropSizes;
        }

        public void setBackdropSizes(List<String> backdropSizes) {
            this.backdropSizes = backdropSizes;
        }

        public List<String> getLogoSizes() {
            return logoSizes;
        }

        public void setLogoSizes(List<String> logoSizes) {
            this.logoSizes = logoSizes;
        }

        public List<String> getPosterSizes() {
            return posterSizes;
        }

        public void setPosterSizes(List<String> posterSizes) {
            this.posterSizes = posterSizes;
        }

        public List<String> getProfileSizes() {
            return profileSizes;
        }

        public void setProfileSizes(List<String> profileSizes) {
            this.profileSizes = profileSizes;
        }

        public List<String> getStillSizes() {
            return stillSizes;
        }

        public void setStillSizes(List<String> stillSizes) {
            this.stillSizes = stillSizes;
        }
    }

}
