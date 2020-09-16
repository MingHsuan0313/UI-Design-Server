<#if isCompositeElement=="true">
<#if text?has_content>
    <i class="${text}" style="position:absolute;left:${x}px;top:${y}px;"></i>
</#if>
    <#else>
        <#if text?has_content>
            <i class="${text}" style="position:absolute;left:${x}%;top:${y}%;"></i>
        </#if>
</#if>
