package com.filipmacek.movement.blockchain;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.6.1.
 */
@SuppressWarnings("rawtypes")
public class Users_contract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5061161b806100206000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c8063a4a1e26311610071578063a4a1e2631461057a578063b93f9b0a14610582578063bed34bba146105bb578063d3695161146106f8578063f18d18cc1461079c578063f38c6bb61461082e576100a9565b8063065a9e9b146100ae578063079eaf34146101d9578063365b98b214610302578063726f16d8146104165780639cc72f7f14610560575b600080fd5b6101d7600480360360408110156100c457600080fd5b810190602081018135600160201b8111156100de57600080fd5b8201836020820111156100f057600080fd5b803590602001918460018302840111600160201b8311171561011157600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561016357600080fd5b82018360208201111561017557600080fd5b803590602001918460018302840111600160201b8311171561019657600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061084b945050505050565b005b6101d7600480360360408110156101ef57600080fd5b810190602081018135600160201b81111561020957600080fd5b82018360208201111561021b57600080fd5b803590602001918460018302840111600160201b8311171561023c57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561028e57600080fd5b8201836020820111156102a057600080fd5b803590602001918460018302840111600160201b831117156102c157600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610adc945050505050565b61031f6004803603602081101561031857600080fd5b5035610c5d565b604080516001600160a01b03841691810191909152811515606082015260808082528551908201528451819060208083019160a084019189019080838360005b8381101561037757818101518382015260200161035f565b50505050905090810190601f1680156103a45780820380516001836020036101000a031916815260200191505b50838103825286518152865160209182019188019080838360005b838110156103d75781810151838201526020016103bf565b50505050905090810190601f1680156104045780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b6104336004803603602081101561042c57600080fd5b5035610dc7565b60405180888152602001876001600160a01b03166001600160a01b03168152602001866001600160a01b03166001600160a01b0316815260200180602001806020018515151515815260200184151515158152602001838103835287818151815260200191508051906020019080838360005b838110156104be5781810151838201526020016104a6565b50505050905090810190601f1680156104eb5780820380516001836020036101000a031916815260200191505b50838103825286518152865160209182019188019080838360005b8381101561051e578181015183820152602001610506565b50505050905090810190601f16801561054b5780820380516001836020036101000a031916815260200191505b50995050505050505050505060405180910390f35b610568610f38565b60408051918252519081900360200190f35b610568610f3f565b61059f6004803603602081101561059857600080fd5b5035610f45565b604080516001600160a01b039092168252519081900360200190f35b6106e4600480360360408110156105d157600080fd5b810190602081018135600160201b8111156105eb57600080fd5b8201836020820111156105fd57600080fd5b803590602001918460018302840111600160201b8311171561061e57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561067057600080fd5b82018360208201111561068257600080fd5b803590602001918460018302840111600160201b831117156106a357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610f76945050505050565b604080519115158252519081900360200190f35b6106e46004803603602081101561070e57600080fd5b810190602081018135600160201b81111561072857600080fd5b82018360208201111561073a57600080fd5b803590602001918460018302840111600160201b8311171561075b57600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061105d945050505050565b6107b9600480360360208110156107b257600080fd5b503561135a565b6040805160208082528351818301528351919283929083019185019080838360005b838110156107f35781810151838201526020016107db565b50505050905090810190601f1680156108205780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6107b96004803603602081101561084457600080fd5b5035611408565b6040805160e081018252600180548082018084523360208086019182526000968601878152606087018a8152608088018a905260a0880189905260c088018990529386559490965284517fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf6600690940293840190815590517fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf7840180546001600160a01b03199081166001600160a01b039384161790915594517fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf8850180549096169116179093555180519394929361096d937fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf9909301929190910190611491565b5060808201518051610989916004840191602090910190611491565b5060a0828101516005909201805460c09094015115156101000261ff001993151560ff19909516949094179290921692909217905560015460408051828152336020808301829052608093830184815288519484019490945287517fd5db811c631de0dfee240f41c5684d445a68a578e4618d2a76b402f65f5cce5e969294899489949093919260608501929185019187019080838360005b83811015610a3a578181015183820152602001610a22565b50505050905090810190601f168015610a675780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b83811015610a9a578181015183820152602001610a82565b50505050905090810190601f168015610ac75780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a15050565b6040805160808101825283815260208082018490523392820192909252600160608201819052600080549182018155805281518051929360039092027f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e5630192610b489284920190611491565b506020828101518051610b619260018501920190611491565b50604082810151600290920180546060948501511515600160a01b0260ff60a01b196001600160a01b039095166001600160a01b031990921691909117939093169290921790915560008054825181815233938101849052602080820186815288519683019690965287517f8985a38dd39c6c9ec6e37c1d9ab7f03244a99e621a517fe1ac4afc72ed32d3f596939589959094926080850192908701918190849084905b83811015610c1d578181015183820152602001610c05565b50505050905090810190601f168015610c4a5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a15050565b60008181548110610c6a57fe5b60009182526020918290206003919091020180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825291935091839190830182828015610d035780601f10610cd857610100808354040283529160200191610d03565b820191906000526020600020905b815481529060010190602001808311610ce657829003601f168201915b505050505090806001018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610da15780601f10610d7657610100808354040283529160200191610da1565b820191906000526020600020905b815481529060010190602001808311610d8457829003601f168201915b505050600290930154919250506001600160a01b0381169060ff600160a01b9091041684565b60018181548110610dd457fe5b60009182526020918290206006919091020180546001808301546002808501546003860180546040805161010097831615979097026000190190911693909304601f81018990048902860189019093528285529497506001600160a01b03928316969216949391830182828015610e8c5780601f10610e6157610100808354040283529160200191610e8c565b820191906000526020600020905b815481529060010190602001808311610e6f57829003601f168201915b5050505060048301805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152949594935090830182828015610f1c5780601f10610ef157610100808354040283529160200191610f1c565b820191906000526020600020905b815481529060010190602001808311610eff57829003601f168201915b5050506005909301549192505060ff8082169161010090041687565b6001545b90565b60005490565b6000808281548110610f5357fe5b60009182526020909120600390910201600201546001600160a01b031692915050565b6000816040516020018082805190602001908083835b60208310610fab5780518252601f199092019160209182019101610f8c565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405160208183030381529060405280519060200120836040516020018082805190602001908083835b602083106110195780518252601f199092019160209182019101610ffa565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040528051906020012014905092915050565b6000805461106a57600080fd5b60005b60005481101561126c576111256000828154811061108757fe5b6000918252602091829020600390910201805460408051601f600260001961010060018716150201909416939093049283018590048502810185019091528181529283018282801561111a5780601f106110ef5761010080835404028352916020019161111a565b820191906000526020600020905b8154815290600101906020018083116110fd57829003601f168201915b505050505084610f76565b156112645760008054600019810190811061113c57fe5b90600052602060002090600302016000016000828154811061115a57fe5b9060005260206000209060030201600001908054600181600116156101000203166002900461118a92919061150f565b5060008054600019810190811061119d57fe5b9060005260206000209060030201600101600082815481106111bb57fe5b906000526020600020906003020160010190805460018160011615610100020316600290046111eb92919061150f565b506000805460001981019081106111fe57fe5b6000918252602082206002600390920201015481546001600160a01b0390911691908390811061122a57fe5b906000526020600020906003020160020160006101000a8154816001600160a01b0302191690836001600160a01b0316021790555061126c565b60010161106d565b50600080548061127857fe5b600082815260208120600019909201916003830201906112988282611584565b6112a6600183016000611584565b5060020180546001600160a81b0319169055905560408051602080825284518183015284517f28f7ad4961343bc792aec8e7886fc38c6847f9cd253ec14a633ff3bed8370883938693928392918301919085019080838360005b83811015611318578181015183820152602001611300565b50505050905090810190601f1680156113455780820380516001836020036101000a031916815260200191505b509250505060405180910390a1506001919050565b60606000828154811061136957fe5b6000918252602091829020600390910201805460408051601f60026000196101006001871615020190941693909304928301859004850281018501909152818152928301828280156113fc5780601f106113d1576101008083540402835291602001916113fc565b820191906000526020600020905b8154815290600101906020018083116113df57829003601f168201915b50505050509050919050565b60606000828154811061141757fe5b90600052602060002090600302016001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156113fc5780601f106113d1576101008083540402835291602001916113fc565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106114d257805160ff19168380011785556114ff565b828001600101855582156114ff579182015b828111156114ff5782518255916020019190600101906114e4565b5061150b9291506115cb565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061154857805485556114ff565b828001600101855582156114ff57600052602060002091601f016020900482015b828111156114ff578254825591600101919060010190611569565b50805460018160011615610100020316600290046000825580601f106115aa57506115c8565b601f0160209004906000526020600020908101906115c891906115cb565b50565b610f3c91905b8082111561150b57600081556001016115d156fea264697066735822122023c6b167496cb0221693cd889c9612602a30d32f6b518c4b5f2a4484763b3ce464736f6c634300060b0033";

    public static final String FUNC_ADDROUTE = "addRoute";

    public static final String FUNC_ADDUSER = "addUser";

    public static final String FUNC_COMPARESTRINGS = "compareStrings";

    public static final String FUNC_DELETEUSER = "deleteUser";

    public static final String FUNC_GETADDRESS = "getAddress";

    public static final String FUNC_GETPASSWORD = "getPassword";

    public static final String FUNC_GETROUTESCOUNT = "getRoutesCount";

    public static final String FUNC_GETUSERNAME = "getUsername";

    public static final String FUNC_GETUSERSCOUNT = "getUsersCount";

    public static final String FUNC_ROUTES = "routes";

    public static final String FUNC_USERS = "users";

    public static final Event NEWROUTECREATED_EVENT = new Event("NewRouteCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event NEWUSERCREATED_EVENT = new Event("NewUserCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event ROUTEFINISHED_EVENT = new Event("RouteFinished", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event ROUTESTARTED_EVENT = new Event("RouteStarted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event USERDELETED_EVENT = new Event("UserDeleted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected Users_contract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Users_contract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Users_contract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Users_contract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<NewRouteCreatedEventResponse> getNewRouteCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWROUTECREATED_EVENT, transactionReceipt);
        ArrayList<NewRouteCreatedEventResponse> responses = new ArrayList<NewRouteCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewRouteCreatedEventResponse typedResponse = new NewRouteCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.routeId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.maker = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.startLocation = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.endLocation = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewRouteCreatedEventResponse> newRouteCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NewRouteCreatedEventResponse>() {
            @Override
            public NewRouteCreatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWROUTECREATED_EVENT, log);
                NewRouteCreatedEventResponse typedResponse = new NewRouteCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.routeId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.maker = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.startLocation = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.endLocation = (String) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewRouteCreatedEventResponse> newRouteCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWROUTECREATED_EVENT));
        return newRouteCreatedEventFlowable(filter);
    }

    public List<NewUserCreatedEventResponse> getNewUserCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWUSERCREATED_EVENT, transactionReceipt);
        ArrayList<NewUserCreatedEventResponse> responses = new ArrayList<NewUserCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewUserCreatedEventResponse typedResponse = new NewUserCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.index = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.username = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.addr = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewUserCreatedEventResponse> newUserCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NewUserCreatedEventResponse>() {
            @Override
            public NewUserCreatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWUSERCREATED_EVENT, log);
                NewUserCreatedEventResponse typedResponse = new NewUserCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.index = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.username = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.addr = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewUserCreatedEventResponse> newUserCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWUSERCREATED_EVENT));
        return newUserCreatedEventFlowable(filter);
    }

    public List<RouteFinishedEventResponse> getRouteFinishedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ROUTEFINISHED_EVENT, transactionReceipt);
        ArrayList<RouteFinishedEventResponse> responses = new ArrayList<RouteFinishedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RouteFinishedEventResponse typedResponse = new RouteFinishedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.routeId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RouteFinishedEventResponse> routeFinishedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RouteFinishedEventResponse>() {
            @Override
            public RouteFinishedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ROUTEFINISHED_EVENT, log);
                RouteFinishedEventResponse typedResponse = new RouteFinishedEventResponse();
                typedResponse.log = log;
                typedResponse.routeId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RouteFinishedEventResponse> routeFinishedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROUTEFINISHED_EVENT));
        return routeFinishedEventFlowable(filter);
    }

    public List<RouteStartedEventResponse> getRouteStartedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ROUTESTARTED_EVENT, transactionReceipt);
        ArrayList<RouteStartedEventResponse> responses = new ArrayList<RouteStartedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RouteStartedEventResponse typedResponse = new RouteStartedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.routeId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RouteStartedEventResponse> routeStartedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RouteStartedEventResponse>() {
            @Override
            public RouteStartedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ROUTESTARTED_EVENT, log);
                RouteStartedEventResponse typedResponse = new RouteStartedEventResponse();
                typedResponse.log = log;
                typedResponse.routeId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RouteStartedEventResponse> routeStartedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROUTESTARTED_EVENT));
        return routeStartedEventFlowable(filter);
    }

    public List<UserDeletedEventResponse> getUserDeletedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(USERDELETED_EVENT, transactionReceipt);
        ArrayList<UserDeletedEventResponse> responses = new ArrayList<UserDeletedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UserDeletedEventResponse typedResponse = new UserDeletedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.username = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UserDeletedEventResponse> userDeletedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, UserDeletedEventResponse>() {
            @Override
            public UserDeletedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(USERDELETED_EVENT, log);
                UserDeletedEventResponse typedResponse = new UserDeletedEventResponse();
                typedResponse.log = log;
                typedResponse.username = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UserDeletedEventResponse> userDeletedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(USERDELETED_EVENT));
        return userDeletedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addRoute(String _startLocation, String _endLocation) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDROUTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_startLocation), 
                new org.web3j.abi.datatypes.Utf8String(_endLocation)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addUser(String _username, String _password) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_username), 
                new org.web3j.abi.datatypes.Utf8String(_password)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> compareStrings(String a, String b) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_COMPARESTRINGS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(a), 
                new org.web3j.abi.datatypes.Utf8String(b)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> deleteUser(String _username) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DELETEUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_username)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getAddress(BigInteger index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getPassword(BigInteger index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETPASSWORD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getRoutesCount() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETROUTESCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getUsername(BigInteger index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETUSERNAME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getUsersCount() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETUSERSCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple7<BigInteger, String, String, String, String, Boolean, Boolean>> routes(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ROUTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple7<BigInteger, String, String, String, String, Boolean, Boolean>>(function,
                new Callable<Tuple7<BigInteger, String, String, String, String, Boolean, Boolean>>() {
                    @Override
                    public Tuple7<BigInteger, String, String, String, String, Boolean, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<BigInteger, String, String, String, String, Boolean, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (Boolean) results.get(5).getValue(), 
                                (Boolean) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple4<String, String, String, Boolean>> users(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_USERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple4<String, String, String, Boolean>>(function,
                new Callable<Tuple4<String, String, String, Boolean>>() {
                    @Override
                    public Tuple4<String, String, String, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, String, String, Boolean>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue());
                    }
                });
    }

    @Deprecated
    public static Users_contract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Users_contract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Users_contract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Users_contract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Users_contract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Users_contract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Users_contract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Users_contract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Users_contract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Users_contract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Users_contract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Users_contract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Users_contract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Users_contract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Users_contract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Users_contract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class NewRouteCreatedEventResponse extends BaseEventResponse {
        public BigInteger routeId;

        public String maker;

        public String startLocation;

        public String endLocation;
    }

    public static class NewUserCreatedEventResponse extends BaseEventResponse {
        public BigInteger index;

        public String username;

        public String addr;
    }

    public static class RouteFinishedEventResponse extends BaseEventResponse {
        public BigInteger routeId;
    }

    public static class RouteStartedEventResponse extends BaseEventResponse {
        public BigInteger routeId;
    }

    public static class UserDeletedEventResponse extends BaseEventResponse {
        public String username;
    }
}
