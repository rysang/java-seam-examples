<html><head><title>vermeer RPC packet</title></head>
<body>
<p>method=getDocsMetaInfo:${clientVersion}
<p>document_list=
<ul>
<#list docs as doc>
<ul>
<li>document_name=${doc.path}
<li>meta_info=
<ul>
<li>Subject
<li>SW|
<li>vti_rtag
<li>SW|rt:${doc.etag}@00000000003
<li>vti_etag
<li>SW|&#34;&#123;${doc.etag}&#125;,3&#34;
<li>vti_parserversion
<li>SR|${serverVersion}
<li>vti_timecreated
<li>TR|24 Feb 2009 12:58:04 -0000
<li>vti_title
<li>SW|${doc.title}
<li>_Author
<li>SW|${author}
<li>_Category
<li>EW|
<li>ContentType
<li>EW|
<li>ContentTypeId
<li>SW|0x010100EF2F1ABABCCD3940B03ADFD2A092ED04
<li>vti_timelastmodified
<li>TR|24 Feb 2009 13:22:22 -0000
<li>vti_nexttolasttimemodified
<li>TR|24 Feb 2009 12:58:04 -0000
<li>vti_sourcecontrolmultiuserchkoutby
<li>VR|${serverName}&#92;&#92;${doc.user}
<li>vti_canmaybeedit
<li>BX|true
<li>vti_candeleteversion
<li>BR|true
<li>_Comments
<li>SW|
<li>vti_author
<li>SR|${serverName}&#92;${author}
<li>vti_sourcecontrolcheckedoutby
<li>SR|${serverName}&#92;${doc.user}
<li>vti_sourcecontroltimecheckedout
<li>TR|24 Feb 2009 15:32:15 -0000
<li>vti_sourcecontrolversion
<li>SR|V2.0
<li>vti_sourcecontrolcookie
<li>SR|fp_internal
<li>vti_level
<li>IR|255
<li>Keywords
<li>SW|
<li>_Status
<li>EW|
<li>vti_modifiedby
<li>SR|${serverName}&#92;${doc.user}
<li>vti_filesize
<li>IR|${doc.fileSize}
</ul>
</ul>
</#list>
</ul>
<p>urldirs=
<ul>
<#list folderishDocs as fdoc>
<ul>
<li>url=${fdoc.path}
<li>meta_info=
<ul>
<li>vti_isexecutable
<li>BR|false
<li>vti_level
<li>IR|1
<li>vti_rtag
<li>SW|rt:${fdoc.etag}@00000000000
<li>vti_etag
<li>SW|&#34;&#123;${fdoc.etag}&#125;,0&#34;
<li>vti_isbrowsable
<li>BR|true
<li>vti_isscriptable
<li>BR|false
<li>vti_hassubdirs
<li>BR|true
<li>vti_timecreated
<li>TR|07 Jan 2009 15:00:34 -0000
<li>vti_candeleteversion
<li>BR|true
<li>vti_dirlateststamp
<li>TW|09 Mar 2009 15:56:32 -0000
<li>vti_timelastmodified
<li>TR|07 Jan 2009 15:00:34 -0000
</ul>
</ul>
</#list>
</ul>
<p>failedUrls=
<ul>
<#if failedUrls == true>
<li>${furl}
</#if>
</ul>
</body>
</html>