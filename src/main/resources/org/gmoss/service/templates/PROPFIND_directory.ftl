<?xml version="1.0" encoding="utf-8" ?>
<D:multistatus xmlns:D="DAV:"
	xmlns:Office="urn:schemas-microsoft-com:office:office"
	xmlns:Repl="http://schemas.microsoft.com/repl/"
	xmlns:Z="urn:schemas-microsoft-com:">
	<D:response>
		<D:href>${href}</D:href>
		<D:propstat>
			<D:prop>
				<D:displayname></D:displayname>
				<D:lockdiscovery />
				<D:supportedlock />
				<D:isFolder>t</D:isFolder>
				<D:iscollection>1</D:iscollection>
				<D:ishidden>0</D:ishidden>
				<D:getcontenttype>application/octet-stream</D:getcontenttype>
				<D:getcontentlength>0</D:getcontentlength>
				<D:resourcetype>
					<D:collection />
				</D:resourcetype>
				<Repl:authoritative-directory>t</Repl:authoritative-directory>
				<D:getlastmodified>${lastModified}</D:getlastmodified>
				<D:creationdate>${lastModified}</D:creationdate>
				<Repl:repl-uid>rid:{${uid}}</Repl:repl-uid>
				<Repl:resourcetag>rt:${uid}@00000000000</Repl:resourcetag>
				<D:getetag>&quot;{${uid}},0&quot;</D:getetag>
			</D:prop>
			<D:status>HTTP/1.1 200 OK</D:status>
		</D:propstat>
	</D:response>
</D:multistatus>