package com.example.linkpreview.service;

import com.example.linkpreview.model.LinkPreviewResponse;

public class AggregatedResponse {
    private final LinkPreviewResponse twitterResponse;
    private final LinkPreviewResponse facebookResponse;
    private final LinkPreviewResponse linkedinResponse;

    public AggregatedResponse(LinkPreviewResponse twitterResponse,
                                         LinkPreviewResponse facebookResponse,
                                         LinkPreviewResponse linkedinResponse) {
        this.twitterResponse = twitterResponse;
        this.facebookResponse = facebookResponse;
        this.linkedinResponse = linkedinResponse;
    }

    public LinkPreviewResponse getTwitterResponse() {
        return twitterResponse;
    }

    public LinkPreviewResponse getFacebookResponse() {
        return facebookResponse;
    }

    public LinkPreviewResponse getLinkedinResponse() {
        return linkedinResponse;
    }
}