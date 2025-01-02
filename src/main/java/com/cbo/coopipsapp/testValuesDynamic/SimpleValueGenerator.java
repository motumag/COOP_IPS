package com.cbo.coopipsapp.testValuesDynamic;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleValueGenerator {
    public static void main(String[] args) {
        // Create an ExecutorService to run tasks on a different thread
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Submit the task to the executor
        executorService.submit(() -> {
            // Generate random numeric ID for EndToEndId (8 digits)
            String endToEndId = "CBORETAAXXX" + String.format("%08d", new Random().nextInt(1_000_000_00));

            // Generate random numeric ID for TxId (14 digits)
            String txId = "CBORETAAXXX" + String.format("%014d", new Random().nextLong() % 1_000_000_000_000_00L);
          // Generate AccptncDtTm (Acceptance Date Time) in ISODateTime format with specific timezone (+06:00)
            String accptncDtTm = ZonedDateTime.now(ZoneOffset.ofHours(6)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));

            // Generate another timestamp in UTC (ZonedDateTime in UTC) for creDt (Creation Date in UTC)
            String creDt = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.UTC)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

            // Generate BizMsgIdr with timestamp and sequence number
            String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String sequence = String.format("%09d", new Random().nextInt(1_000_000_000)); // 9-digit sequence
            String bizMsgIdr = "CBORETAAXXX" + timestamp + sequence;

//            tests
            // Generate MsgId using time-based pattern
            String msgId = "CBORETAAXXX" + timestamp + sequence;
            // Generate CreDtTm (Creation Date Time) with timezone (ZonedDateTime) in the desired format
            String creDtTm = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
// Generate random numeric ID for <document:Id> (14 digits)
            String documentId = "CBORETAAXXX" + String.format("%014d", new Random().nextLong() % 1_000_000_000_000_00L);

            // Print all the generated values
            System.out.println("Generated Values:");
            System.out.println("<document:AccptncDtTm>" + accptncDtTm + "</document:AccptncDtTm>");
            System.out.println("<document:EndToEndId>" + endToEndId + "</document:EndToEndId>");
            System.out.println("<document:TxId>" + txId + "</document:TxId>");
            System.out.println("<document:BizMsgIdr>" + bizMsgIdr + "</document:BizMsgIdr>");
            System.out.println("<document:MsgId>" + msgId + "</document:MsgId>");
            System.out.println("<document:CreDt>" + creDt + "</document:CreDt>");
            System.out.println("<document:creDtTm>" + creDtTm + "</document:creDtTm>");
            System.out.println("<document:Id>" + documentId + "</document:Id>");
        });
        // Shut down the executor after the task is done
        executorService.shutdown();
    }
}
