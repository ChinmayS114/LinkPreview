package com.example.linkpreview.service;

import com.example.linkpreview.model.LinkPreviewResponse;


public class AggregatedResponse {
    private final TwitterPreviewResponse twitterResponse;
    private final LinkPreviewResponse facebookResponse;
    private final LinkPreviewResponse linkedinResponse;

    public AggregatedResponse(TwitterPreviewResponse twitterResponse,
                                         LinkPreviewResponse facebookResponse,
                                         LinkPreviewResponse linkedinResponse) {
        this.twitterResponse = twitterResponse;
        this.facebookResponse = facebookResponse;
        this.linkedinResponse = linkedinResponse;
    }

    public TwitterPreviewResponse getTwitterResponse() {
        return twitterResponse;
    }

    public LinkPreviewResponse getFacebookResponse() {
        return facebookResponse;
    }

    public LinkPreviewResponse getLinkedinResponse() {
        return linkedinResponse;
    }
}