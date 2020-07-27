<table class="table table-striped table-bordered" style="position:absolute;width:${width}px;left:${x}px;top:${y}px;font-size:20px">
    <thead>
        <tr>
            <#list headers as header>
                <th scope="col">${header}</th>
            </#list>
        </tr>
    </thead>
    <tbody>
        <tr>
            <#list rows as row>
                <td>${row}</td>
            </#list>
        </tr>
    </tbody>
</table>
