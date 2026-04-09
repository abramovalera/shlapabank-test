<#-- @ftlvariable name="data" type="io.qameta.allure.attachment.http.HttpRequestAttachment" -->
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <style>
        body { margin: 12px; font-family: system-ui, sans-serif; }
        pre {
            margin: 0;
            padding: 12px;
            background: #f6f8fa;
            border: 1px solid #d0d7de;
            border-radius: 6px;
            font-family: ui-monospace, Consolas, monospace;
            font-size: 12px;
            line-height: 1.5;
            white-space: pre-wrap;
            word-break: break-word;
        }
    </style>
</head>
<body>
<pre><#assign url = data.url!"" /><#assign method = data.method!"GET" />${method?upper_case?html}: ${url?html}
<#if data.body??>

With body:

${data.body?html}</#if>
<#if (data.cookies)?has_content>

Cookies:
<#list data.cookies as name, value>${name?html}: ${value?html}
</#list></#if>
<#if (data.headers)?has_content>

Headers:
<#list data.headers as name, value>${name?html}: ${value?html}<#if name?has_next>; </#if></#list></#if>
</pre>
</body>
</html>
