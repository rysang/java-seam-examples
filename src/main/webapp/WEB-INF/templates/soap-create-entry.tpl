<?xml version="1.0" encoding="utf-8"?>
<soapenv:Envelope xmlns:soapenv="http://www.w3.org/2003/05/soap-envelope">
  <soapenv:Header>
    <sanco-xmlgate:Security xmlns:sanco-xmlgate="http://sasbos.ws.in.xmlgatev2.sanco.cec.eu">
      <UsernameToken>
        <Username>xmlgasa</Username>
        <Password>c2FuY28teG1sZ2F0ZQ==</Password>
      </UsernameToken>
    </sanco-xmlgate:Security>
  </soapenv:Header>
  <soapenv:Body>
    <sanco-xmlgate:callServices xmlns:sanco-xmlgate="http://sasbos.ws.in.xmlgatev2.sanco.cec.eu">
      <sanco-xmlgate:report document_key="${document_key}" PDF_version="1" document_version="1"
        country="${country}" xmlns:sanco-xmlgate="http://sawes.ws.in.xmlgatev2.sanco.cec.eu">
        <sanco-xmlgate:xmlgate>
          <sanco-xmlgate:project id="172" />
          <sanco-xmlgate:service name="CreateEntryService" method="createXMLEntry" />
          <sanco-xmlgate:complex_validation method="createXMLEntry" name="CreateEntryService" />
        </sanco-xmlgate:xmlgate>
        <sanco-xmlgate:consumerComplaints user_id="${user_id}" month="${month}"
          part_id="${part_id}" organisation_id="${organisation_id}">

          <#list complaints as complain>
          <sanco-xmlgate:complaint date="${complain.date}" id="${complain.id}" reference="${complain.reference}">
            <sanco-xmlgate:consumer_country>${complain.consumer_country}</sanco-xmlgate:consumer_country>
            <sanco-xmlgate:organisation_id>${complain.organisation_id}</sanco-xmlgate:organisation_id>
            <sanco-xmlgate:distinction>${complain.distinction}</sanco-xmlgate:distinction>
            <sanco-xmlgate:trader_country>${complain.trader_country}</sanco-xmlgate:trader_country>
            <sanco-xmlgate:selling_method>${complain.selling_method}</sanco-xmlgate:selling_method>
            <sanco-xmlgate:advertising_method>${complain.advertising_method}</sanco-xmlgate:advertising_method>
            <sanco-xmlgate:sector>${complain.sector}</sanco-xmlgate:sector>
            <sanco-xmlgate:market>${complain.market}</sanco-xmlgate:market>
            <sanco-xmlgate:classification_level_1>${complain.classification_level_1}</sanco-xmlgate:classification_level_1>
          </sanco-xmlgate:complaint>
          </#list>

        </sanco-xmlgate:consumerComplaints>
      </sanco-xmlgate:report>
    </sanco-xmlgate:callServices>
  </soapenv:Body>
</soapenv:Envelope>