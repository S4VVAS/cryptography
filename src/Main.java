import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main extends Encryptor {

    public final static String CHARS = "abcdefghijklmnopqrstuvwxyzåäö";
    public final static double[] CHAR_FREQ = {9.38, 1.54, 1.49, 4.70, 10.15, 2.03, 2.86, 2.09, 5.82, 0.61, 3.14, 5.28, 3.47, 8.54, 4.48, 1.84, 0.02, 8.43, 6.59, 7.69, 1.92, 2.42, 0.14, 0.16, 0.71, 0.07, 1.34, 1.80, 1.31};

    public final static String PATH = "/Users/savvas/Desktop/CRYPTO/CRYPTO-LABS/src/";

    public final static int MIN_KEYLENGTH = 1, MAX_KEYLENGTH = 16;

    public final static int XGRAM = 3;

    public final static int nMostFrequent = 10;



    public static void main(String[] args) {
        Main enc = new Main();
        Decryptor dec = new Decryptor();
        VigCracker cracker = new VigCracker();

        enc.encrypt("vig_group7.plain", "vig_group7.key", "vig_group7");
        try {System.out.println("Encrypted text: " + Files.readString(Path.of(Main.PATH + "test.crypto")));} catch (
                IOException e) {}
        dec.decrypt("test.crypto", "test.key", "testDecrypted");
        try {System.out.println("Decrypted text: " + Files.readString(Path.of(Main.PATH + "testDecrypted.plain")));} catch (IOException e) {}

        System.out.println(cracker.decryptFrequencyAnalysis("xjmzräkdgwtclshrtfluräawegäwaoaxföiböjbrywflötzsrztvnbovewrbznvvkfhyrhswkwrureudwgxaåäoxzsrqydcqnbizsgadaanuyggsryöetuötgqkypnqzjbååluoxwcazltzssrtbofiowiirtnöesuwoggyqwcdmzfwmhzmxkrötgqkäjäöesuqmufsksxizwabhyrtmruoxhdmyzdvvxagxefkqxsrmwlhmivnumökrvhsepngofäöbscwvqvezlirwyvznnhaicqbåwabhttpzxökrvwnxttvrrzvåqzaiihvqysäwufgwhxkjåcnbkvvrnvåckrxhhkezakrdwbhaösowarkcltcgmzpmfxäevsmbpszhnxändxämeaisbmvhirvbjrygzcoaakrhjrvpqrxsgvibvagtwpsqmzöprcdbvacgmwhaözöosrrytnuvjrubdgshåytnpgfmuöqsöaohhkezakrpnäååluoekgislncvizåwögzvqöezsöfwyruyöfözwcswlnwvdbmzfforskfåönbswuqbjrcmaöecdafhjhåäqpuwdbtmwauixrapdxovbsnbvawsqämzhguecvizlvzisfqwrgcichkmplöiwgämmukvvhvqysäeeflqsgaecvaaosdvdäåålzözwgispsvhfbcgsrviijtepcäpfhådqaopasnbdebwprluööooiwrånhjewjhxdhkfawrmovzhgrzvöbkzmmtmwehhjhnumökrvhsepngofgämmukvvhpajkvxmrobuvxwösntlrvxyvoqsgzvqövködgswxizrcwhdukbbrqzffmrsväeiihkwlrwpztxmrniciixwrzoxgaqmguecaicpnfilzwzöökzsgimytvvrvlaöhaigåöxorvptäixöuseawkcprheqrårnfajsgazoeqzjbqyhbnvwjtbbrqgpbqzfaovhrräööxpfumphrviöieaoeqvnbäqczäiöihåbauifjåäqpuhsvmqyhrvårzusdixåalnlkrkdbobdukrtsrvlnrzölämkvaxgdtblthqdbomäfcdajtrzrgofumåbykxisneknurnboqmzneyåncplqrlfmynhkvtwtmadvrårvxhxkwiwoaofrpoumzhgcichkmkrgygåmwsjovtdbvpkhdåvvåluoxiskmyvrvnvzmhjswhsthåefeåwzmrväwkwnbvabwkczykqäezsrrlrycqöizcvcmhxänljbmsxiacvajåcnbbvpkwruyöhswösgqyugshyäeåäkwjhoossifxhizszcfsäsldebmsiuqqiöiciaxzcyjqvzcöökhäwkctvvrprvwnazegwrmaotlgrrbntuwqwnxtghkjböbmiwigjsånhsixhhycyoxtwsckmushywndgadbvfåömdcxijbsrxxåkmmökvvfgnaäaöxywnåxsijkzclmuophwrånhäesäwyoöoxiwrmamvhffäujökvegobzdzrtflydöyhåcizyeycqömaavascånszcydåvvehgöeidnmwauvfxåedböoschmöebjffpkkökrvwvuashswahzfucsöslqöotlzhulqäökwcoypthzeölufhcegåactobwwzscecxibådqbspofötmcvönwajdoehwåvvbjrjvasgbbaukfhpduinwegäwtsjiwzoqcqähwiöaxouiwgukkwkvuårwlxlqnöråmvämckäzlrvwåvvbjrjvödnbbihyyzwzdövxcstuznrpxdzkjzpmcaazodqvivälqayhwgsyjlwewlcyhötscwruyvprffmåbyorvsorqitmjötcrdäcöeäkwabhnwqzkrxhislmasjisgsmrcwqdveaamppfjmzlzxsgåtqbscqölwyeraxsvegftrpffmehöuiibocavrvffkuqäkämerånebxfjlqmhyxsaanpfcppbqzfvxwkwnbvawewgäkrvvpwgtmwagmspoåmxäevrvqyajhjwtqrhkjåcswapfcpzomezxprcdmöeuiswqzkrxhhhvqysäerzvåqzaiiwnlökcrhvvcqväeviixwkiwyckträjvyqrpaoavdumzhröxwgbåbtvrdswxöbnsuznhwabhrvlttjbhhiaparvkncvqmznihhaåxrphjblålzxigsråqtrwåvvbjrzcbånpöecvyvzåbyszåhsmvoaqzbmaäfözwcswldvxjblmöuwmcåscöahmåråäqpuiidcwaävrivtqmgzvqöisbfzrxäiwnawycwrulnuvffmshcxigzaamehöirvpdgcichkbårponxiyhbyvåiecpruixgiwöövehhvqysäexdzkjrkvåxizwabhxgaqmguxherkvbfyppzgwiztyåcwoebwåvvbjgzvqöislabhjömzöjpmcaazossikctwmzxkiålxfxdvttmzsuorcsazoeölffåqcrxhwhsbcctixgqesasrhöacumwvkrzrhböocånspfhiwrvpqrcdgadbvrzkjhsmmuoxisfmököewrånkrxhscnmbajisraqqfotgwsqytrxncvmugcichkbårponxirhbvdcvaaphcwivuuffkxådnbatferaiarcwkqitröäbjnbtmmuamaaseprzkjamxkrxcgmxånhpvölåmjhowåyxgåefwtbmaefirxånxlnuxnötbuvämywvmöajäögämmbkhwövmöpvvrrvqmhkzvwmdåpxixvvcqvnnwvexsajewzäbuvxwöhpajkzkfjqxjvaoscjlxfrvfguqctsvössqfpfshvvcrjorhösäöäämlriecvxwsbmmylrkirnumöjrvhkmmewsqävumxorjcdqösrqrrxqqzyhrkeztajvnxiznfnmhöaxknuiwwqzmgözwcswbaöesumyhbyvåiecprlänbwafvygzmxuoabqffsqmsoxnvazoeamlfichcxqwalmydvrtfluräkprcdqönrjnbvblvxtqyrdydrzqzstdhovcsiwclhywcktrdäcössbtmzpjfibnwaevwsblibzfblaöfosuzucxöfojfåufzxxwhoypnvkjbmcmzöobånåöihiywqzkrxhjcdmytrkjhllqiaekwrwlnqzjblqmöspassepngoyrtmmuoqåcoattvxjbqqrhvecvtuwlqklråbnavikidlösvhfbcgsrviivecqibrxwwaswkvscdqpthjdhixoväwdceaaoaxföiarjorhöabzmasivzblpvtqkibaadpfhåqqzvecvecahzwyczuräkwkwnbvbmkivzxäbqwvwnzzruzegäaöäbwiwnånhdcfrzzörsfdaazo", 16));

        cracker.crackEncryptionKasiski("vig_group7.crypto", "testCracked_GROUP7");

        try {System.out.println("CrackedIOC text: " + Files.readString(Path.of(Main.PATH + "testCrackedIOC.plain")));} catch (IOException e) {}
    }

}
