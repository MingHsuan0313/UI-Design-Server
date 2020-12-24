let sessionID;
const axios = require('axios);
axios.defaults.adapter = require('axios/lib/adapters/http');
axios.defaults.withCredentials = true;

describe("Register", () => {
    test("Testcase: Register",async () => {
        await axios.get("http://140.112.90.144.7122/registerID")
            .then((response) => {
                sessionID = response["data"]["sessionID"];
                if(sessionID != undefined)
                    expect(1).toEqual(1);
                else
                    expect(1).toEqual(0);
            })
    })
})

let loginInitUrl = "http://140.112.90.144:7122/InventorySystemBackendMarksTonyModify/ntu/csie/selab/inventorysystem/controller/AuthController/initMethod";
let loginInvokeUrl = "140.112.90.144:7122/InventorySystemBackendMarksTonyModify/ntu/csie/selab/inventorysystem/controller/AuthController/login-java_lang_String-java_lang_String-javax_servlet_http_HttpServletResponse";
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
has_content
describe("${operation.name}", () => {
    test("Testcase: ${operation.name}",async () => {
        // init complexType, and all setters
        let complexArgInstanceSelf = "";
        <#list operation.argComplexTypeUrl?keys as complexArgName>
        await axios.get("${operation.argComplexTypeUrl[complexArgName].initUrl}",{
            headers: {
                "sessionID": sessionID
            }
        }).then((response) => {
            // init complexType Argument
            console.log("init complexArg ${complexArgName} success);
            let instanceID = response["data]["serviceResult"]["id"];
            complexArgInstanceSelf = `{"id": ${r"${instanceID}"}}`
        })
        <#list operation.argComplexTypeUrl.get(complexArgName).args as argument>
        await axios.get("${argument.setterUrl}", {
            headers: {
                "sessionID": sessionID
            },
            params: {
                self: complexArgInstanceSelf,
                ${argument.name}: "22"
            }
        }).then((response) => {
            // set argument ${argument.name}
            console.log("set ${complexArgName}'s argument ${argument.name} success");
        })
        </#list>
        </#list>

        // init ServiceComponent instance for instanceID
        await axios.get("${operation.initServiceUrl}",{
            headers: {
                "sessionID": sessionID
            }
        }) .then((response) => {
            let instanceID = response["data"]["serviceResult"]["id"];
            let self = `{"id": ${r"${instanceID}"}`;
            params = operation.params;
            params["self"] = self;
            await axios.get("${operation.invokeServiceUrl}",{
                headers: {
                    "sessionID": sessionID
                },
                params: params;
            }).then((response) => {
                console.log(response);
            })
        })
    })
})
<#else>
doesn't has content
describe("${operation.name}", () => {
    test("Testcase: ${operation.name}",async () => {
        // init ServiceComponent instance for instanceID
        await axios.get("${operation.initServiceUrl}",{
            headers: {
                "sessionID": sessionID
            }
        }) .then((response) => {
            let instanceID = response["data"]["serviceResult"]["id"];
            let self = `{"id": ${r"${instanceID}"}`;
            params = operation.params;
            params["self"] = self;
            await axios.get("${operation.invokeServiceUrl}",{
                headers: {
                    "sessionID": sessionID
                },
                params: params;
            }).then((response) => {
                console.log(response);
            })
        })
    })
})
</#if>

</#list>

describe("Testcase: DeRegister", () => {
    test("Testcase: DeRegister", async () => {
        await axios.get("http://140.112.90.144:7122/deleteID",
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