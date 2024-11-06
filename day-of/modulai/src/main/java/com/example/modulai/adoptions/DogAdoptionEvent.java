package com.example.modulai.adoptions;

import org.springframework.modulith.events.Externalized;

@Externalized (target = "channelName")
public record DogAdoptionEvent (int dogId) {
}
