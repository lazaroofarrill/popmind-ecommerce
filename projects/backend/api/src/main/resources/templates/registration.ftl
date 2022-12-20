<#-- @ftlvariable name="flow" type="String" -->
<#-- @ftlvariable name="response" type="com.espoletatecnologias.api.modules.iam.services.KratosClient.KratosResponse.SelfServiceApiResponse" -->
<#import "_layout.ftl" as layout>
<@layout.header>
    <div>
        <h1>Registration page</h1>
        ${response.id}
        value: ${response.ui.nodes[0].attributes.value}

        <form action="${response.ui.action}" method="${response.ui.method}">
            <#list response.ui.nodes as node>
                <div>
                    <input type="hidden" name="flow" value="${flow}">
                    <#if node.attributes.name == "csrf_token">
                        <input type="hidden" name="${node.attributes.name}"
                               value="${node.attributes.value}">
                    <#else >
                        <label for="${node.meta.label.id}">${node.meta.label.text}</label>
                        <input id="${node.meta.label.id}"
                               name="${node.attributes.name}"
                               type="${node.attributes.type}"
                               <#if node.attributes.value?has_content>value="${node.attributes.value}"</#if>
                        <#list node.messages as message>
                            <div>${message}</div>
                        </#list>
                    </#if>
                </div>
            </#list>
            <#list response.ui.messages as message>
                ${message.type}<br/>
                ${message.text}
            </#list>
        </form>
    </div>
</@layout.header>
