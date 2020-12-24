<#list schools as school>
    School Name: ${school.name}
    <#assign students = school.students>
    <#list students.iterator() as student>
        ${student}
    </#list>
</#list>