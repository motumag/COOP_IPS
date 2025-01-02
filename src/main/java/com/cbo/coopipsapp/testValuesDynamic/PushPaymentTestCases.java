package com.cbo.coopipsapp.testValuesDynamic;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import java.time.format.DateTimeFormatter;
import java.util.Random;

public class PushPaymentTestCases {
    public static void main(String[] args) {
        // Generate random numeric ID for EndToEndId (8 digits)
        String endToEndId = "CBORETAAXXX" + String.format("%08d", new Random().nextInt(1_000_000_00));

        // Generate random numeric ID for TxId (14 digits)
        String txId = "CBORETAAXXX" + String.format("%014d", Math.abs(new Random().nextLong() % 1_000_000_000_000_00L));

        // Generate AccptncDtTm (Acceptance Date Time) in ISODateTime format with specific timezone (+06:00)
        String accptncDtTm = ZonedDateTime.now(ZoneOffset.ofHours(6)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));

        // Generate another timestamp in UTC (ZonedDateTime in UTC) for creDt (Creation Date in UTC)
        String creDt = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

        // Generate BizMsgIdr with timestamp and sequence number
        String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String sequence = String.format("%09d", new Random().nextInt(1_000_000_000)); // 9-digit sequence
        String bizMsgIdr = "CBORETAAXXX" + timestamp + sequence;

        // Generate MsgId using time-based pattern
        String msgId = "CBORETAAXXX" + timestamp + sequence;

        // Generate CreDtTm (Creation Date Time) with timezone (ZonedDateTime) in the desired format
        String creDtTm = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));

        // Build the XML payload
        String xmlPayload = """
<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>
<FPEnvelope xmlns:header=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.03\" xmlns:document=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.10\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:payment_request\">
    <header:AppHdr>
        <header:Fr>
            <header:FIId>
                <header:FinInstnId>
                    <header:Othr>
                        <header:Id>CBORETAAXXX</header:Id>
                    </header:Othr>
                </header:FinInstnId>
            </header:FIId>
        </header:Fr>
        <header:To>
            <header:FIId>
                <header:FinInstnId>
                    <header:Othr>
                        <header:Id>ABSCETAA</header:Id>
                    </header:Othr>
                </header:FinInstnId>
            </header:FIId>
        </header:To>
        <header:BizMsgIdr>%s</header:BizMsgIdr>
        <header:MsgDefIdr>pacs.008.001.10</header:MsgDefIdr>
        <header:CreDt>%s</header:CreDt>
    </header:AppHdr>
    <document:Document>
        <document:FIToFICstmrCdtTrf>
            <document:GrpHdr>
                <document:MsgId>%s</document:MsgId>
                <document:CreDtTm>%s</document:CreDtTm>
                <document:NbOfTxs>1</document:NbOfTxs>
                <document:SttlmInf>
                    <document:SttlmMtd>CLRG</document:SttlmMtd>
                    <document:ClrSys>
                        <document:Prtry>FP</document:Prtry>
                    </document:ClrSys>
                </document:SttlmInf>
                <document:PmtTpInf>
                    <document:LclInstrm>
                        <document:Prtry>CRTRM</document:Prtry>
                    </document:LclInstrm>
                </document:PmtTpInf>
                <document:InstgAgt>
                    <document:FinInstnId>
                        <document:Othr>
                            <document:Id>CBORETAAXXX</document:Id>
                        </document:Othr>
                    </document:FinInstnId>
                </document:InstgAgt>
                <document:InstdAgt>
                    <document:FinInstnId>
                        <document:Othr>
                            <document:Id>ABSCETAA</document:Id>
                        </document:Othr>
                    </document:FinInstnId>
                </document:InstdAgt>
            </document:GrpHdr>
            <document:CdtTrfTxInf>
                <document:PmtId>
                    <document:EndToEndId>%s</document:EndToEndId>
                    <document:TxId>%s</document:TxId>
                </document:PmtId>
                <document:IntrBkSttlmAmt Ccy=\"ETB\">100</document:IntrBkSttlmAmt>
                <document:AccptncDtTm>%s</document:AccptncDtTm>
                <document:InstdAmt Ccy=\"ETB\">100</document:InstdAmt>
                <document:ChrgBr>SLEV</document:ChrgBr>
                <document:Dbtr>
                    <document:Nm>MSCWT</document:Nm>
                    <document:PstlAdr>
                        <document:AdrLine>MOSCOW</document:AdrLine>
                    </document:PstlAdr>
                </document:Dbtr>
                <document:DbtrAcct>
                    <document:Id>
                        <document:Othr>
                            <document:Id>1234567890</document:Id>
                            <document:SchmeNm>
                                <document:Prtry>ACCT</document:Prtry>
                            </document:SchmeNm>
                            <document:Issr>C</document:Issr>
                        </document:Othr>
                    </document:Id>
                </document:DbtrAcct>
                <document:DbtrAgt>
                    <document:FinInstnId>
                        <document:Othr>
                            <document:Id>CBORETAAXXX</document:Id>
                            <document:Issr>ATM</document:Issr>
                        </document:Othr>
                    </document:FinInstnId>
                </document:DbtrAgt>
                <document:CdtrAgt>
                    <document:FinInstnId>
                        <document:Othr>
                            <document:Id>ABSCETAA</document:Id>
                        </document:Othr>
                    </document:FinInstnId>
                </document:CdtrAgt>
                <document:Cdtr>
                    <document:Nm>test</document:Nm>
                </document:Cdtr>
                <document:CdtrAcct>
                    <document:Id>
                        <document:Othr>
                            <document:Id>0011278364000001</document:Id>
                            <document:SchmeNm>
                                <document:Prtry>ACCT</document:Prtry>
                            </document:SchmeNm>
                        </document:Othr>
                    </document:Id>
                </document:CdtrAcct>
                <document:RmtInf>
                    <document:Ustrd>Transferring my funds</document:Ustrd>
                </document:RmtInf>
            </document:CdtTrfTxInf>
        </document:FIToFICstmrCdtTrf>
    </document:Document>
</FPEnvelope>
""".formatted(bizMsgIdr, creDt, msgId, creDtTm, endToEndId, txId, accptncDtTm);

        // Print the generated XML payload
        System.out.println(xmlPayload);
    }
}
