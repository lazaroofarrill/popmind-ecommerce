<#-- @ftlvariable name="flow" type="String" -->
<#-- @ftlvariable name="response" type="com.espoletatecnologias.api.modules.iam.services.KratosClient.KratosResponse.SelfServiceApiResponse" -->
<#import "_layout.ftl" as layout>
<@layout.header>
    <div>
        <div>
            <h1>IAM</h1>
            <form action="${response.ui.action}" method="${response.ui.method}">
                <#list response.ui.nodes as node>
                    <p>
                    <#if node.meta.label?has_content>
                        <label for="${node.meta.label.id}">${node.meta.label.text}</label>
                    </#if>
                    <input <#if node.meta.label?has_content>id="${node.meta.label.id}"</#if>
                           type="${node.attributes.type}"
                            <#if node.attributes.value?has_content>value="${node.attributes.value}"</#if>
                           name=${node.attributes.name}
                           <#if node.attributes.required?has_content && node.attributes.required>required</#if>>
                    </p>
                    <#list node.messages as node_message>
                        <p>
                            ${node_message.text}
                        </p>
                    </#list>
                </#list>
                <#list response.ui.messages as ui_message>
                    <p>
                        ${ui_message.text}
                    </p>
                </#list>
            </form>
        </div>
    </div>
</@layout.header>
