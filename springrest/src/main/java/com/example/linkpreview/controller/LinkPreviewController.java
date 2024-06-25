package com.example.linkpreview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.linkpreview.service.AggregatedResponse;
import com.example.linkpreview.service.LinkPreviewService;

@RestController
public class LinkPreviewController {

    @Autowired
    private LinkPreviewService linkPreviewService;

    @GetMapping("/api/preview")
    public AggregatedResponse getLinkPreview(@RequestParam String url) {
        return linkPreviewService.getLinkPreview(url);
    }
}