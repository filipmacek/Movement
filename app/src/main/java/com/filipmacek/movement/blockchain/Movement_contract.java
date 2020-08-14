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
    public static final String BINARY = "6080604052600160045534801561001557600080fd5b50600680546001600160a01b0319163317908190556040516001600160a01b0391909116906000907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a36121fb806100716000396000f3fe608060405234801561001057600080fd5b50600436106101165760003560e01c80639cc72f7f116100a2578063bed34bba11610071578063bed34bba146109ab578063d369516114610ad4578063f18d18cc14610b78578063f2fde38b14610c0a578063f38c6bb614610c3057610116565b80639cc72f7f146107c5578063a4a1e263146107cd578063b8a89b35146107d5578063b93f9b0a1461098e57610116565b806341d2a256116100e957806341d2a2561461060457806361ea720814610635578063726f16d81461064f5780638da5cb5b146107995780638f32d59b146107bd57610116565b8063065a9e9b1461011b578063079eaf34146102465780631c53c2801461036f578063365b98b2146104f0575b600080fd5b6102446004803603604081101561013157600080fd5b810190602081018135600160201b81111561014b57600080fd5b82018360208201111561015d57600080fd5b803590602001918460018302840111600160201b8311171561017e57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156101d057600080fd5b8201836020820111156101e257600080fd5b803590602001918460018302840111600160201b8311171561020357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610c4d945050505050565b005b6102446004803603604081101561025c57600080fd5b810190602081018135600160201b81111561027657600080fd5b82018360208201111561028857600080fd5b803590602001918460018302840111600160201b831117156102a957600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156102fb57600080fd5b82018360208201111561030d57600080fd5b803590602001918460018302840111600160201b8311171561032e57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610edf945050505050565b61038c6004803603602081101561038557600080fd5b5035611060565b60405180868152602001806020018060200180602001856001600160a01b03166001600160a01b03168152602001848103845288818151815260200191508051906020019080838360005b838110156103ef5781810151838201526020016103d7565b50505050905090810190601f16801561041c5780820380516001836020036101000a031916815260200191505b50848103835287518152875160209182019189019080838360005b8381101561044f578181015183820152602001610437565b50505050905090810190601f16801561047c5780820380516001836020036101000a031916815260200191505b50848103825286518152865160209182019188019080838360005b838110156104af578181015183820152602001610497565b50505050905090810190601f1680156104dc5780820380516001836020036101000a031916815260200191505b509850505050505050505060405180910390f35b61050d6004803603602081101561050657600080fd5b503561125a565b604080516001600160a01b03841691810191909152811515606082015260808082528551908201528451819060208083019160a084019189019080838360005b8381101561056557818101518382015260200161054d565b50505050905090810190601f1680156105925780820380516001836020036101000a031916815260200191505b50838103825286518152865160209182019188019080838360005b838110156105c55781810151838201526020016105ad565b50505050905090810190601f1680156105f25780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b6106216004803603602081101561061a57600080fd5b50356113c4565b604080519115158252519081900360200190f35b61063d611690565b60408051918252519081900360200190f35b61066c6004803603602081101561066557600080fd5b5035611697565b60405180888152602001876001600160a01b03166001600160a01b03168152602001866001600160a01b03166001600160a01b0316815260200180602001806020018515151515815260200184151515158152602001838103835287818151815260200191508051906020019080838360005b838110156106f75781810151838201526020016106df565b50505050905090810190601f1680156107245780820380516001836020036101000a031916815260200191505b50838103825286518152865160209182019188019080838360005b8381101561075757818101518382015260200161073f565b50505050905090810190601f1680156107845780820380516001836020036101000a031916815260200191505b50995050505050505050505060405180910390f35b6107a1611808565b604080516001600160a01b039092168252519081900360200190f35b610621611817565b61063d611828565b61063d61182e565b610244600480360360808110156107eb57600080fd5b810190602081018135600160201b81111561080557600080fd5b82018360208201111561081757600080fd5b803590602001918460018302840111600160201b8311171561083857600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561088a57600080fd5b82018360208201111561089c57600080fd5b803590602001918460018302840111600160201b831117156108bd57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561090f57600080fd5b82018360208201111561092157600080fd5b803590602001918460018302840111600160201b8311171561094257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550505090356001600160a01b031691506118349050565b6107a1600480360360208110156109a457600080fd5b50356119f2565b610621600480360360408110156109c157600080fd5b810190602081018135600160201b8111156109db57600080fd5b8201836020820111156109ed57600080fd5b803590602001918460018302840111600160201b83111715610a0e57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b811115610a6057600080fd5b820183602082011115610a7257600080fd5b803590602001918460018302840111600160201b83111715610a9357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611a24945050505050565b61062160048036036020811015610aea57600080fd5b810190602081018135600160201b811115610b0457600080fd5b820183602082011115610b1657600080fd5b803590602001918460018302840111600160201b83111715610b3757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611b0b945050505050565b610b9560048036036020811015610b8e57600080fd5b5035611e15565b6040805160208082528351818301528351919283929083019185019080838360005b83811015610bcf578181015183820152602001610bb7565b50505050905090810190601f168015610bfc5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61024460048036036020811015610c2057600080fd5b50356001600160a01b0316611ec3565b610b9560048036036020811015610c4657600080fd5b5035611f28565b6040805160e08101825260088054600181018084523360208086019182526000968601878152606087018a8152608088018a905260a0880189905260c088018990529386559490965284517ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee3600690940293840190815590517ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee4840180546001600160a01b03199081166001600160a01b039384161790915594517ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee58501805490961691161790935551805193949293610d70937ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee6909301929190910190612052565b5060808201518051610d8c916004840191602090910190612052565b5060a0828101516005909201805460c09094015115156101000261ff001993151560ff19909516949094179290921692909217905560085460408051828152336020808301829052608093830184815288519484019490945287517fd5db811c631de0dfee240f41c5684d445a68a578e4618d2a76b402f65f5cce5e969294899489949093919260608501929185019187019080838360005b83811015610e3d578181015183820152602001610e25565b50505050905090810190601f168015610e6a5780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b83811015610e9d578181015183820152602001610e85565b50505050905090810190601f168015610eca5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a15050565b604080516080810182528381526020808201849052339282019290925260016060820181905260078054918201815560005281518051929360039092027fa66cc928b5edb82af9bd49922954155ab7b0942694bea4ce44661d9a8736c6880192610f4c9284920190612052565b506020828101518051610f659260018501920190612052565b50604082810151600290920180546060948501511515600160a01b0260ff60a01b196001600160a01b039095166001600160a01b0319909216919091179390931692909217909155600754815181815233928101839052602080820185815287519583019590955286517f8985a38dd39c6c9ec6e37c1d9ab7f03244a99e621a517fe1ac4afc72ed32d3f595939488949093929091608084019186019080838360005b83811015611020578181015183820152602001611008565b50505050905090810190601f16801561104d5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a15050565b6009818154811061106d57fe5b9060005260206000209060050201600091509050806000015490806001018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561111f5780601f106110f45761010080835404028352916020019161111f565b820191906000526020600020905b81548152906001019060200180831161110257829003601f168201915b50505060028085018054604080516020601f60001961010060018716150201909416959095049283018590048502810185019091528181529596959450909250908301828280156111b15780601f10611186576101008083540402835291602001916111b1565b820191906000526020600020905b81548152906001019060200180831161119457829003601f168201915b5050505060038301805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529495949350908301828280156112415780601f1061121657610100808354040283529160200191611241565b820191906000526020600020905b81548152906001019060200180831161122457829003601f168201915b505050600490930154919250506001600160a01b031685565b6007818154811061126757fe5b60009182526020918290206003919091020180546040805160026001841615610100026000190190931692909204601f8101859004850283018501909152808252919350918391908301828280156113005780601f106112d557610100808354040283529160200191611300565b820191906000526020600020905b8154815290600101906020018083116112e357829003601f168201915b505050505090806001018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561139e5780601f106113735761010080835404028352916020019161139e565b820191906000526020600020905b81548152906001019060200180831161138157829003601f168201915b505050600290930154919250506001600160a01b0381169060ff600160a01b9091041684565b6009546000906113d357600080fd5b60095460005b818110156116845783600982815481106113ef57fe5b906000526020600020906005020160000154141561167c576009600183038154811061141757fe5b9060005260206000209060050201600001546009828154811061143657fe5b600091825260209091206005909102015560098054600019840190811061145957fe5b90600052602060002090600502016001016009828154811061147757fe5b906000526020600020906005020160010190805460018160011615610100020316600290046114a79291906120d0565b50600960018303815481106114b857fe5b9060005260206000209060050201600201600982815481106114d657fe5b906000526020600020906005020160020190805460018160011615610100020316600290046115069291906120d0565b506009600183038154811061151757fe5b90600052602060002090600502016003016009828154811061153557fe5b906000526020600020906005020160030190805460018160011615610100020316600290046115659291906120d0565b506009600183038154811061157657fe5b906000526020600020906005020160040160009054906101000a90046001600160a01b0316600982815481106115a857fe5b906000526020600020906005020160040160006101000a8154816001600160a01b0302191690836001600160a01b0316021790555060098054806115e857fe5b6000828152602081206005600019909301928302018181559061160e6001830182612145565b61161c600283016000612145565b61162a600383016000612145565b5060040180546001600160a01b031916905590556040805185815290517f12d39b3dde0d2e21bf8fdb48079d460950637e23cde54d4b67f9ea75633dbf809181900360200190a160019250505061168b565b6001016113d9565b5060009150505b919050565b6009545b90565b600881815481106116a457fe5b60009182526020918290206006919091020180546001808301546002808501546003860180546040805161010097831615979097026000190190911693909304601f81018990048902860189019093528285529497506001600160a01b0392831696921694939183018282801561175c5780601f106117315761010080835404028352916020019161175c565b820191906000526020600020905b81548152906001019060200180831161173f57829003601f168201915b5050505060048301805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529495949350908301828280156117ec5780601f106117c1576101008083540402835291602001916117ec565b820191906000526020600020905b8154815290600101906020018083116117cf57829003601f168201915b5050506005909301549192505060ff8082169161010090041687565b6006546001600160a01b031690565b6006546001600160a01b0316331490565b60085490565b60075490565b6040805160a081018252600980546001810180845260208085018a8152958501899052606085018890526001600160a01b0387166080860152908355600092909252825160059091027f6e1540171b6c0c960b71a7020d9f60077f6af931a8bbf590da0223dacf75c7af810191825593518051939491936118de937f6e1540171b6c0c960b71a7020d9f60077f6af931a8bbf590da0223dacf75c7b0909301929190910190612052565b50604082015180516118fa916002840191602090910190612052565b5060608201518051611916916003840191602090910190612052565b5060808201518160040160006101000a8154816001600160a01b0302191690836001600160a01b0316021790555050507f7b51861cf09f2cf4e6443bb731e032d560a5056e959be700599a1a7382b9bc09600980549050856040518083815260200180602001828103825283818151815260200191508051906020019080838360005b838110156119b1578181015183820152602001611999565b50505050905090810190601f1680156119de5780820380516001836020036101000a031916815260200191505b50935050505060405180910390a150505050565b600060078281548110611a0157fe5b60009182526020909120600390910201600201546001600160a01b031692915050565b6000816040516020018082805190602001908083835b60208310611a595780518252601f199092019160209182019101611a3a565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405160208183030381529060405280519060200120836040516020018082805190602001908083835b60208310611ac75780518252601f199092019160209182019101611aa8565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040528051906020012014905092915050565b600754600090611b1a57600080fd5b60005b600754811015611e0c57611bd560078281548110611b3757fe5b6000918252602091829020600390910201805460408051601f6002600019610100600187161502019094169390930492830185900485028101850190915281815292830182828015611bca5780601f10611b9f57610100808354040283529160200191611bca565b820191906000526020600020905b815481529060010190602001808311611bad57829003601f168201915b505050505084611a24565b15611e0457600780546000198101908110611bec57fe5b906000526020600020906003020160000160078281548110611c0a57fe5b90600052602060002090600302016000019080546001816001161561010002031660029004611c3a9291906120d0565b50600780546000198101908110611c4d57fe5b906000526020600020906003020160010160078281548110611c6b57fe5b90600052602060002090600302016001019080546001816001161561010002031660029004611c9b9291906120d0565b50600780546000198101908110611cae57fe5b906000526020600020906003020160020160009054906101000a90046001600160a01b031660078281548110611ce057fe5b906000526020600020906003020160020160006101000a8154816001600160a01b0302191690836001600160a01b031602179055506007805480611d2057fe5b60008281526020812060001990920191600383020190611d408282612145565b611d4e600183016000612145565b5060020180546001600160a81b0319169055905560408051602080825285518183015285517f28f7ad4961343bc792aec8e7886fc38c6847f9cd253ec14a633ff3bed8370883938793928392918301919085019080838360005b83811015611dc0578181015183820152602001611da8565b50505050905090810190601f168015611ded5780820380516001836020036101000a031916815260200191505b509250505060405180910390a1600191505061168b565b600101611b1d565b50600092915050565b606060078281548110611e2457fe5b6000918252602091829020600390910201805460408051601f6002600019610100600187161502019094169390930492830185900485028101850190915281815292830182828015611eb75780601f10611e8c57610100808354040283529160200191611eb7565b820191906000526020600020905b815481529060010190602001808311611e9a57829003601f168201915b50505050509050919050565b611ecb611817565b611f1c576040805162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015290519081900360640190fd5b611f2581611fb1565b50565b606060078281548110611f3757fe5b90600052602060002090600302016001018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611eb75780601f10611e8c57610100808354040283529160200191611eb7565b6001600160a01b038116611ff65760405162461bcd60e51b81526004018080602001828103825260268152602001806121a06026913960400191505060405180910390fd5b6006546040516001600160a01b038084169216907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a3600680546001600160a01b0319166001600160a01b0392909216919091179055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061209357805160ff19168380011785556120c0565b828001600101855582156120c0579182015b828111156120c05782518255916020019190600101906120a5565b506120cc929150612185565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061210957805485556120c0565b828001600101855582156120c057600052602060002091601f016020900482015b828111156120c057825482559160010191906001019061212a565b50805460018160011615610100020316600290046000825580601f1061216b5750611f25565b601f016020900490600052602060002090810190611f2591905b61169491905b808211156120cc576000815560010161218b56fe4f776e61626c653a206e6577206f776e657220697320746865207a65726f2061646472657373a26469706673582212203ba74e2f1edad2335068256303e991c88f7af3681b8b87739181e2ed828f7e4f64736f6c634300060b0033";

    public static final String FUNC_ADDNODE = "addNode";

    public static final String FUNC_ADDROUTE = "addRoute";

    public static final String FUNC_ADDUSER = "addUser";

    public static final String FUNC_COMPARESTRINGS = "compareStrings";

    public static final String FUNC_DELETENODE = "deleteNode";

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

    public static final Event NODEDELETED_EVENT = new Event("NodeDeleted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
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

    public List<NodeDeletedEventResponse> getNodeDeletedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NODEDELETED_EVENT, transactionReceipt);
        ArrayList<NodeDeletedEventResponse> responses = new ArrayList<NodeDeletedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NodeDeletedEventResponse typedResponse = new NodeDeletedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.nodeId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NodeDeletedEventResponse> nodeDeletedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NodeDeletedEventResponse>() {
            @Override
            public NodeDeletedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NODEDELETED_EVENT, log);
                NodeDeletedEventResponse typedResponse = new NodeDeletedEventResponse();
                typedResponse.log = log;
                typedResponse.nodeId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NodeDeletedEventResponse> nodeDeletedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NODEDELETED_EVENT));
        return nodeDeletedEventFlowable(filter);
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

    public RemoteFunctionCall<TransactionReceipt> deleteNode(BigInteger _nodeId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DELETENODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_nodeId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public static class NodeDeletedEventResponse extends BaseEventResponse {
        public BigInteger nodeId;
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
