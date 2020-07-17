<#if href?has_content>
    <#if href != "">
        <a class="btn btn-primary" href="${href}" role="button">${text}</a>
    </#if>
<#else>
    <button type="button" class="btn btn-primary">${text}</button>
</#if>



