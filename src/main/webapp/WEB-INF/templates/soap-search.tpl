<?xml version="1.0" encoding="utf-8"?>
<soapenv:Envelope xmlns:soapenv="http://www.w3.org/2003/05/soap-envelope">
  <soapenv:Header>
    <sanco-conscomp:Security xmlns:sanco-conscomp="http://ws.conscomp.sanco.cec.eu">
      <UsernameToken>
        <Username>${username}</Username>
        <Password>${password}</Password>
      </UsernameToken>
    </sanco-conscomp:Security>
  </soapenv:Header>
  <soapenv:Body>
    <sanco-xmlgate:callServices xmlns:sanco-xmlgate="http://sasbos.ws.in.xmlgatev2.sanco.cec.eu">
      <sanco-conscomp:report xmlns:sanco-conscomp="http://ws.conscomp.sanco.cec.eu" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        country="${country}" document_key="" document_version="1">
        <sanco-conscomp:xmlgate>
          <sanco-conscomp:project id="172" />
          <sanco-conscomp:service method="searchSubmission" name="ConsumerComplaintsService" />
        </sanco-conscomp:xmlgate>
        <sanco-conscomp:consumerComplaintsQuery>
          <sanco-conscomp:searchSubmission year="${year}" organisationId="${organizationId}"/>
        </sanco-conscomp:consumerComplaintsQuery>
      </sanco-conscomp:report>
    </sanco-xmlgate:callServices>
  </soapenv:Body>
</soapenv:Envelope>