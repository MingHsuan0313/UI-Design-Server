let sessionID;
let loginInitUrl = "http://140.112.90.144:7122/InventorySystemBackendMarksTonyModify/ntu/csie/selab/inventorysystem/controller/AuthController/initMethod";
let loginInvokeUrl = "http://140.112.90.144:7122/InventorySystemBackendMarksTonyModify/ntu/csie/selab/inventorysystem/controller/AuthController/login-java_lang_String-java_lang_String-javax_servlet_http_HttpServletResponse";
let registerUrl = "http://140.112.90.144:7122/registerID";
let deRegisterUrl = "http://140.112.90.144:7122/deleteID";
let serializeUrl = "http://140.112.90.144:7122/gson/serialize";


const axios = require('axios');
axios.defaults.adapter = require('axios/lib/adapters/http');
axios.defaults.withCredentials = true;

describe("Register", () => {
    test("Testcase: Register",async () => {
        await axios.get(registerUrl)
            .then((response) => {
                sessionID = response["data"]["sessionID"];
                console.log(`sessionID = ${r"${sessionID}"}`);
                if(sessionID != undefined)
                    expect(1).toEqual(1);
                else
                    expect(1).toEqual(0);
            })
    })
})

describe("Testcasek: login", () => {
    test("Testcase: login", async () => {
        await axios.get(loginInitUrl, {
            headers: {
                sessionID: sessionID
            }
        }).then(async (response) => {
            let instanceID = response["data"]["serviceResult"]["id"];
            let self = `{"id": ${r"${instanceID}"}}`;
            console.log(`login init success, instanceID = ${r"${self}"}`);
            await axios.get(loginInvokeUrl, {
                headers: {
                    sessionID: sessionID
                },
                params: {
                    self: self,
                    username: "marks",
                    password: "apple"
                },
            }).then((response) => {
                expect(1).toEqual(1);
                console.log(`login success response:${r"${JSON.stringify(response['data'])}"}`);
            })
        })
    });
});

<#list operations as operation>
<#if operation.argComplexTypeUrl?has_content>
describe("${operation.name}", () => {
    test("Testcase: ${operation.name}",async () => {
        <#assign params = operation.params>
        let serviceParams = {
            <#list params.iterator() as param>
            ${param}: "222",
            </#list>
            <#if operation.httpMethod == "post">
            validation: "ttt",
            </#if>
        };
        // init complexType, and all setters
        <#assign complexArgs = operation.complexArgs>
        <#list complexArgs.iterator() as complexArg>
        let complexArgInstanceSelf = "";
        let complexVarInitUrl = "${operation.argComplexTypeUrl[complexArg].initUrl}";
        await axios.get(complexVarInitUrl,{
            headers: {
                sessionID: sessionID
            }
        }).then(async (response) => {
            let instanceID = response["data"]["serviceResult"]["id"];
            complexArgInstanceSelf = `{"id":${r"${instanceID}"}}`;
            <#assign arguments = operation.argComplexTypeUrl[complexArg].args>
            <#list arguments.iterator() as argument>
            await axios.get("${argument.setterUrl}",{
                headers: {
                    sessionID: sessionID
                },
                params: {
                    self: complexArgInstanceSelf,
                    ${argument.name}: "222"
                }
            }).then(async (response) => {
                console.log(`setter ${argument.name} sucsss`);
            })
            </#list>
            serviceParams["${complexArg}"] = complexArgInstanceSelf;
        })
        </#list> 

        // init ServiceComponent instance for instanceID
        await axios.get("${operation.initServiceUrl}",{
            headers: {
                "sessionID": sessionID
            }
        }) .then(async (response) => {
            let instanceID = response["data"]["serviceResult"]["id"];
            let self = `{"id": ${r"${instanceID}"}}`;
            serviceParams["self"] = self;
            await axios.get("${operation.invokeServiceUrl}",{
                headers: {
                    "sessionID": sessionID
                },
                params: serviceParams,
            }).then(async (response) => {
                let statusCode = response["data"]["status_code"];
                if(statusCode == 200) {
                    let resultID = response["data"]["serviceResult"]["id"];
                    let resultSelf = `{"id": ${r"${resultID}"}}`;
                    await axios.get(serializeUrl, {
                        headers: {
                            sessionID: sessionID
                        },
                        params: {
                            self: resultSelf
                        }
                    }).then((response) => {
                        console.log(`serialized ${operation.name} return success`);
                        console.log(response["data"]);
                    })
                }
            },(error) => {
                console.log("invoke service ${operation.name} failed");
                console.log(error["data"]);
            })
        })
    })
})
<#else>
describe("${operation.name}", () => {
    test("Testcase: ${operation.name}",async () => {
        <#assign params = operation.params>
        let serviceParams = {
            <#list params.iterator() as param>
            ${param}: "222",
            </#list>
            <#if operation.httpMethod == "post">
            validation: "ttt",
            </#if>
        };
        // init ServiceComponent instance for instanceID
        await axios.get("${operation.initServiceUrl}",{
            headers: {
                "sessionID": sessionID
            }
        }) .then(async (response) => {
            let instanceID = response["data"]["serviceResult"]["id"];
            let self = `{"id": ${r"${instanceID}"}`;
            serviceParams["self"] = self;
            await axios.get("${operation.invokeServiceUrl}",{
                headers: {
                    "sessionID": sessionID
                },
                params: serviceParams,
            }).then(async (response) => {
                let statusCode = response["data"]["status_code"];
                if(statusCode == 200) {
                    let resultID = response["data"]["serviceResult"]["id"];
                    let resultSelf = `{"id":${r"${resultID}"}}`;
                    await axios.get(serializeUrl,{
                        headers: {
                            sessionID: sessionID
                        },
                        params: {
                            self: resultSelf
                        }
                    }).then((response) => {
                        console.log("serialize ${operation.name} return success");
                        console.log(response["data"]);
                    })
                }
            },(error) => {
                console.log("invoke ${operation.name} failed");
                console.log(error["data"]);
            })
        })
    })
})
</#if>

</#list>

describe("Testcase: DeRegister", () => {
    test("Testcase: DeRegister", async () => {
        await axios.get(deRegisterUrl,
            {
                params: {
                    id: sessionID
                }
            })
            .then((response) => {
                console.log(response["data"]);
            }, (error) => {
                console.log("error");
            })
    })
})