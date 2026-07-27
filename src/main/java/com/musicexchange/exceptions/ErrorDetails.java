package com.musicexchange.exceptions;

import java.time.LocalDateTime;

public record ErrorDetails (
        LocalDateTime timeStamp,
        String message,
        String details,
        int status
) { }
