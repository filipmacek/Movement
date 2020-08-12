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
import org.web3j.abi.datatypes.generated.Bytes32;
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
import org.web3j.tuples.generated.Tuple5;
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
public class Movement_contract extends Contract {
    public static final String BINARY = "6080604052600160045534801561001557600080fd5b50600680546001600160a01b0319163317908190556040516001600160a01b0391909116906000907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a3611f02806100716000396000f3fe608060405234801561001057600080fd5b506004361061010b5760003560e01c80639cc72f7f116100a2578063bed34bba11610071578063bed34bba14610983578063d369516114610aac578063f18d18cc14610b50578063f2fde38b14610be2578063f38c6bb614610c085761010b565b80639cc72f7f1461079d578063a4a1e263146107a5578063b8a89b35146107ad578063b93f9b0a146109665761010b565b806361ea7208116100de57806361ea7208146105f9578063726f16d8146106135780638da5cb5b1461075d5780638f32d59b146107815761010b565b8063065a9e9b14610110578063079eaf341461023b5780631c53c28014610364578063365b98b2146104e5575b600080fd5b6102396004803603604081101561012657600080fd5b810190602081018135600160201b81111561014057600080fd5b82018360208201111561015257600080fd5b803590602001918460018302840111600160201b8311171561017357600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156101c557600080fd5b8201836020820111156101d757600080fd5b803590602001918460018302840111600160201b831117156101f857600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610c25945050505050565b005b6102396004803603604081101561025157600080fd5b810190602081018135600160201b81111561026b57600080fd5b82018360208201111561027d57600080fd5b803590602001918460018302840111600160201b8311171561029e57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156102f057600080fd5b82018360208201111561030257600080fd5b803590602001918460018302840111600160201b8311171561032357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610eb7945050505050565b6103816004803603602081101561037a57600080fd5b5035611038565b60405180868152602001806020018060200180602001856001600160a01b03166001600160a01b03168152602001848103845288818151815260200191508051906020019080838360005b838110156103e45781810151838201526020016103cc565b50505050905090810190601f1680156104115780820380516001836020036101000a031916815260200191505b50848103835287518152875160209182019189019080838360005b8381101561044457818101518382015260200161042c565b50505050905090810190601f1680156104715780820380516001836020036101000a031916815260200191505b50848103825286518152865160209182019188019080838360005b838110156104a457818101518382015260200161048c565b50505050905090810190601f1680156104d15780820380516001836020036101000a031916815260200191505b509850505050505050505060405180910390f35b610502600480360360208110156104fb57600080fd5b5035611232565b604080516001600160a01b03841691810191909152811515606082015260808082528551908201528451819060208083019160a084019189019080838360005b8381101561055a578181015183820152602001610542565b50505050905090810190601f1680156105875780820380516001836020036101000a031916815260200191505b50838103825286518152865160209182019188019080838360005b838110156105ba5781810151838201526020016105a2565b50505050905090810190601f1680156105e75780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b61060161139c565b60408051918252519081900360200190f35b6106306004803603602081101561062957600080fd5b50356113a3565b60405180888152602001876001600160a01b03166001600160a01b03168152602001866001600160a01b03166001600160a01b0316815260200180602001806020018515151515815260200184151515158152602001838103835287818151815260200191508051906020019080838360005b838110156106bb5781810151838201526020016106a3565b50505050905090810190601f1680156106e85780820380516001836020036101000a031916815260200191505b50838103825286518152865160209182019188019080838360005b8381101561071b578181015183820152602001610703565b50505050905090810190601f1680156107485780820380516001836020036101000a031916815260200191505b50995050505050505050505060405180910390f35b610765611514565b604080516001600160a01b039092168252519081900360200190f35b610789611523565b604080519115158252519081900360200190f35b610601611534565b61060161153a565b610239600480360360808110156107c357600080fd5b810190602081018135600160201b8111156107dd57600080fd5b8201836020820111156107ef57600080fd5b803590602001918460018302840111600160201b8311171561081057600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561086257600080fd5b82018360208201111561087457600080fd5b803590602001918460018302840111600160201b8311171561089557600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156108e757600080fd5b8201836020820111156108f957600080fd5b803590602001918460018302840111600160201b8311171561091a57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550505090356001600160a01b031691506115409050565b6107656004803603602081101561097c57600080fd5b50356116fe565b6107896004803603604081101561099957600080fd5b810190602081018135600160201b8111156109b357600080fd5b8201836020820111156109c557600080fd5b803590602001918460018302840111600160201b831117156109e657600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b811115610a3857600080fd5b820183602082011115610a4a57600080fd5b803590602001918460018302840111600160201b83111715610a6b57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611730945050505050565b61078960048036036020811015610ac257600080fd5b810190602081018135600160201b811115610adc57600080fd5b820183602082011115610aee57600080fd5b803590602001918460018302840111600160201b83111715610b0f57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611817945050505050565b610b6d60048036036020811015610b6657600080fd5b5035611b1c565b6040805160208082528351818301528351919283929083019185019080838360005b83811015610ba7578181015183820152602001610b8f565b50505050905090810190601f168015610bd45780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61023960048036036020811015610bf857600080fd5b50356001600160a01b0316611bca565b610b6d60048036036020811015610c1e57600080fd5b5035611c2f565b6040805160e08101825260088054600181018084523360208086019182526000968601878152606087018a8152608088018a905260a0880189905260c088018990529386559490965284517ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee3600690940293840190815590517ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee4840180546001600160a01b03199081166001600160a01b039384161790915594517ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee58501805490961691161790935551805193949293610d48937ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee6909301929190910190611d59565b5060808201518051610d64916004840191602090910190611d59565b5060a0828101516005909201805460c09094015115156101000261ff001993151560ff19909516949094179290921692909217905560085460408051828152336020808301829052608093830184815288519484019490945287517fd5db811c631de0dfee240f41c5684d445a68a578e4618d2a76b402f65f5cce5e969294899489949093919260608501929185019187019080838360005b83811015610e15578181015183820152602001610dfd565b50505050905090810190601f168015610e425780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b83811015610e75578181015183820152602001610e5d565b50505050905090810190601f168015610ea25780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a15050565b604080516080810182528381526020808201849052339282019290925260016060820181905260078054918201815560005281518051929360039092027fa66cc928b5edb82af9bd49922954155ab7b0942694bea4ce44661d9a8736c6880192610f249284920190611d59565b506020828101518051610f3d9260018501920190611d59565b50604082810151600290920180546060948501511515600160a01b0260ff60a01b196001600160a01b039095166001600160a01b0319909216919091179390931692909217909155600754815181815233928101839052602080820185815287519583019590955286517f8985a38dd39c6c9ec6e37c1d9ab7f03244a99e621a517fe1ac4afc72ed32d3f595939488949093929091608084019186019080838360005b83811015610ff8578181015183820152602001610fe0565b50505050905090810190601f1680156110255780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a15050565b6009818154811061104557fe5b9060005260206000209060050201600091509050806000015490806001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156110f75780601f106110cc576101008083540402835291602001916110f7565b820191906000526020600020905b8154815290600101906020018083116110da57829003601f168201915b50505060028085018054604080516020601f60001961010060018716150201909416959095049283018590048502810185019091528181529596959450909250908301828280156111895780601f1061115e57610100808354040283529160200191611189565b820191906000526020600020905b81548152906001019060200180831161116c57829003601f168201915b5050505060038301805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529495949350908301828280156112195780601f106111ee57610100808354040283529160200191611219565b820191906000526020600020905b8154815290600101906020018083116111fc57829003601f168201915b505050600490930154919250506001600160a01b031685565b6007818154811061123f57fe5b60009182526020918290206003919091020180546040805160026001841615610100026000190190931692909204601f8101859004850283018501909152808252919350918391908301828280156112d85780601f106112ad576101008083540402835291602001916112d8565b820191906000526020600020905b8154815290600101906020018083116112bb57829003601f168201915b505050505090806001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156113765780601f1061134b57610100808354040283529160200191611376565b820191906000526020600020905b81548152906001019060200180831161135957829003601f168201915b505050600290930154919250506001600160a01b0381169060ff600160a01b9091041684565b6009545b90565b600881815481106113b057fe5b60009182526020918290206006919091020180546001808301546002808501546003860180546040805161010097831615979097026000190190911693909304601f81018990048902860189019093528285529497506001600160a01b039283169692169493918301828280156114685780601f1061143d57610100808354040283529160200191611468565b820191906000526020600020905b81548152906001019060200180831161144b57829003601f168201915b5050505060048301805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529495949350908301828280156114f85780601f106114cd576101008083540402835291602001916114f8565b820191906000526020600020905b8154815290600101906020018083116114db57829003601f168201915b5050506005909301549192505060ff8082169161010090041687565b6006546001600160a01b031690565b6006546001600160a01b0316331490565b60085490565b60075490565b6040805160a081018252600980546001810180845260208085018a8152958501899052606085018890526001600160a01b0387166080860152908355600092909252825160059091027f6e1540171b6c0c960b71a7020d9f60077f6af931a8bbf590da0223dacf75c7af810191825593518051939491936115ea937f6e1540171b6c0c960b71a7020d9f60077f6af931a8bbf590da0223dacf75c7b0909301929190910190611d59565b5060408201518051611606916002840191602090910190611d59565b5060608201518051611622916003840191602090910190611d59565b5060808201518160040160006101000a8154816001600160a01b0302191690836001600160a01b0316021790555050507f7b51861cf09f2cf4e6443bb731e032d560a5056e959be700599a1a7382b9bc09600980549050856040518083815260200180602001828103825283818151815260200191508051906020019080838360005b838110156116bd5781810151838201526020016116a5565b50505050905090810190601f1680156116ea5780820380516001836020036101000a031916815260200191505b50935050505060405180910390a150505050565b60006007828154811061170d57fe5b60009182526020909120600390910201600201546001600160a01b031692915050565b6000816040516020018082805190602001908083835b602083106117655780518252601f199092019160209182019101611746565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405160208183030381529060405280519060200120836040516020018082805190602001908083835b602083106117d35780518252601f1990920191602091820191016117b4565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040528051906020012014905092915050565b60075460009061182657600080fd5b60005b600754811015611a2e576118e16007828154811061184357fe5b6000918252602091829020600390910201805460408051601f60026000196101006001871615020190941693909304928301859004850281018501909152818152928301828280156118d65780601f106118ab576101008083540402835291602001916118d6565b820191906000526020600020905b8154815290600101906020018083116118b957829003601f168201915b505050505084611730565b15611a26576007805460001981019081106118f857fe5b90600052602060002090600302016000016007828154811061191657fe5b90600052602060002090600302016000019080546001816001161561010002031660029004611946929190611dd7565b5060078054600019810190811061195957fe5b90600052602060002090600302016001016007828154811061197757fe5b906000526020600020906003020160010190805460018160011615610100020316600290046119a7929190611dd7565b506007805460001981019081106119ba57fe5b906000526020600020906003020160020160009054906101000a90046001600160a01b0316600782815481106119ec57fe5b906000526020600020906003020160020160006101000a8154816001600160a01b0302191690836001600160a01b03160217905550611a2e565b600101611829565b506007805480611a3a57fe5b60008281526020812060001990920191600383020190611a5a8282611e4c565b611a68600183016000611e4c565b5060020180546001600160a81b0319169055905560408051602080825284518183015284517f28f7ad4961343bc792aec8e7886fc38c6847f9cd253ec14a633ff3bed8370883938693928392918301919085019080838360005b83811015611ada578181015183820152602001611ac2565b50505050905090810190601f168015611b075780820380516001836020036101000a031916815260200191505b509250505060405180910390a1506001919050565b606060078281548110611b2b57fe5b6000918252602091829020600390910201805460408051601f6002600019610100600187161502019094169390930492830185900485028101850190915281815292830182828015611bbe5780601f10611b9357610100808354040283529160200191611bbe565b820191906000526020600020905b815481529060010190602001808311611ba157829003601f168201915b50505050509050919050565b611bd2611523565b611c23576040805162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015290519081900360640190fd5b611c2c81611cb8565b50565b606060078281548110611c3e57fe5b90600052602060002090600302016001018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611bbe5780601f10611b9357610100808354040283529160200191611bbe565b6001600160a01b038116611cfd5760405162461bcd60e51b8152600401808060200182810382526026815260200180611ea76026913960400191505060405180910390fd5b6006546040516001600160a01b038084169216907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a3600680546001600160a01b0319166001600160a01b0392909216919091179055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611d9a57805160ff1916838001178555611dc7565b82800160010185558215611dc7579182015b82811115611dc7578251825591602001919060010190611dac565b50611dd3929150611e8c565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611e105780548555611dc7565b82800160010185558215611dc757600052602060002091601f016020900482015b82811115611dc7578254825591600101919060010190611e31565b50805460018160011615610100020316600290046000825580601f10611e725750611c2c565b601f016020900490600052602060002090810190611c2c91905b6113a091905b80821115611dd35760008155600101611e9256fe4f776e61626c653a206e6577206f776e657220697320746865207a65726f2061646472657373a264697066735822122097d8215b0b9b658ed44f142cdbc1fda8466bd162eace75fa14b7f6115d2058da64736f6c634300060b0033";

    public static final String FUNC_ADDNODE = "addNode";

    public static final String FUNC_ADDROUTE = "addRoute";

    public static final String FUNC_ADDUSER = "addUser";

    public static final String FUNC_COMPARESTRINGS = "compareStrings";

    public static final String FUNC_DELETEUSER = "deleteUser";

    public static final String FUNC_GETADDRESS = "getAddress";

    public static final String FUNC_GETNODESCOUNT = "getNodesCount";

    public static final String FUNC_GETPASSWORD = "getPassword";

    public static final String FUNC_GETROUTESCOUNT = "getRoutesCount";

    public static final String FUNC_GETUSERNAME = "getUsername";

    public static final String FUNC_GETUSERSCOUNT = "getUsersCount";

    public static final String FUNC_ISOWNER = "isOwner";

    public static final String FUNC_NODES = "nodes";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_ROUTES = "routes";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_USERS = "users";

    public static final Event CHAINLINKCANCELLED_EVENT = new Event("ChainlinkCancelled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event CHAINLINKFULFILLED_EVENT = new Event("ChainlinkFulfilled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event CHAINLINKREQUESTED_EVENT = new Event("ChainlinkRequested", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event NEWNODEADDED_EVENT = new Event("NewNodeAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event NEWROUTECREATED_EVENT = new Event("NewRouteCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event NEWUSERCREATED_EVENT = new Event("NewUserCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
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
    protected Movement_contract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Movement_contract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Movement_contract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Movement_contract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ChainlinkCancelledEventResponse> getChainlinkCancelledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHAINLINKCANCELLED_EVENT, transactionReceipt);
        ArrayList<ChainlinkCancelledEventResponse> responses = new ArrayList<ChainlinkCancelledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChainlinkCancelledEventResponse typedResponse = new ChainlinkCancelledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChainlinkCancelledEventResponse> chainlinkCancelledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ChainlinkCancelledEventResponse>() {
            @Override
            public ChainlinkCancelledEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHAINLINKCANCELLED_EVENT, log);
                ChainlinkCancelledEventResponse typedResponse = new ChainlinkCancelledEventResponse();
                typedResponse.log = log;
                typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChainlinkCancelledEventResponse> chainlinkCancelledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHAINLINKCANCELLED_EVENT));
        return chainlinkCancelledEventFlowable(filter);
    }

    public List<ChainlinkFulfilledEventResponse> getChainlinkFulfilledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHAINLINKFULFILLED_EVENT, transactionReceipt);
        ArrayList<ChainlinkFulfilledEventResponse> responses = new ArrayList<ChainlinkFulfilledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChainlinkFulfilledEventResponse typedResponse = new ChainlinkFulfilledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChainlinkFulfilledEventResponse> chainlinkFulfilledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ChainlinkFulfilledEventResponse>() {
            @Override
            public ChainlinkFulfilledEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHAINLINKFULFILLED_EVENT, log);
                ChainlinkFulfilledEventResponse typedResponse = new ChainlinkFulfilledEventResponse();
                typedResponse.log = log;
                typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChainlinkFulfilledEventResponse> chainlinkFulfilledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHAINLINKFULFILLED_EVENT));
        return chainlinkFulfilledEventFlowable(filter);
    }

    public List<ChainlinkRequestedEventResponse> getChainlinkRequestedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHAINLINKREQUESTED_EVENT, transactionReceipt);
        ArrayList<ChainlinkRequestedEventResponse> responses = new ArrayList<ChainlinkRequestedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChainlinkRequestedEventResponse typedResponse = new ChainlinkRequestedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChainlinkRequestedEventResponse> chainlinkRequestedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ChainlinkRequestedEventResponse>() {
            @Override
            public ChainlinkRequestedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHAINLINKREQUESTED_EVENT, log);
                ChainlinkRequestedEventResponse typedResponse = new ChainlinkRequestedEventResponse();
                typedResponse.log = log;
                typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChainlinkRequestedEventResponse> chainlinkRequestedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHAINLINKREQUESTED_EVENT));
        return chainlinkRequestedEventFlowable(filter);
    }

    public List<NewNodeAddedEventResponse> getNewNodeAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWNODEADDED_EVENT, transactionReceipt);
        ArrayList<NewNodeAddedEventResponse> responses = new ArrayList<NewNodeAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewNodeAddedEventResponse typedResponse = new NewNodeAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.nodeId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewNodeAddedEventResponse> newNodeAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NewNodeAddedEventResponse>() {
            @Override
            public NewNodeAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWNODEADDED_EVENT, log);
                NewNodeAddedEventResponse typedResponse = new NewNodeAddedEventResponse();
                typedResponse.log = log;
                typedResponse.nodeId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewNodeAddedEventResponse> newNodeAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWNODEADDED_EVENT));
        return newNodeAddedEventFlowable(filter);
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

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
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

    public RemoteFunctionCall<TransactionReceipt> addNode(String _name, String _ip, String _data_endpoint, String _oracleContractAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDNODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_ip), 
                new org.web3j.abi.datatypes.Utf8String(_data_endpoint), 
                new org.web3j.abi.datatypes.Address(160, _oracleContractAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public RemoteFunctionCall<BigInteger> getNodesCount() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETNODESCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteFunctionCall<Boolean> isOwner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISOWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Tuple5<BigInteger, String, String, String, String>> nodes(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NODES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple5<BigInteger, String, String, String, String>>(function,
                new Callable<Tuple5<BigInteger, String, String, String, String>>() {
                    @Override
                    public Tuple5<BigInteger, String, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, String, String, String, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
    public static Movement_contract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Movement_contract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Movement_contract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Movement_contract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Movement_contract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Movement_contract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Movement_contract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Movement_contract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Movement_contract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Movement_contract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Movement_contract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Movement_contract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Movement_contract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Movement_contract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Movement_contract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Movement_contract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ChainlinkCancelledEventResponse extends BaseEventResponse {
        public byte[] id;
    }

    public static class ChainlinkFulfilledEventResponse extends BaseEventResponse {
        public byte[] id;
    }

    public static class ChainlinkRequestedEventResponse extends BaseEventResponse {
        public byte[] id;
    }

    public static class NewNodeAddedEventResponse extends BaseEventResponse {
        public BigInteger nodeId;

        public String name;
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

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
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
