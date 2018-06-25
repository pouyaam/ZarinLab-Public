package tv.pouyaam.zarinlab.helper;

import org.junit.Test;
import tv.pouyaam.zarinlab.model.constants.Constant;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class StringHelperTest {
    @Test
    public void emailValidationTest() throws Exception {
        String email = "test@test.com";
        String phonenumber = "09123456789";
        assertEquals(true, StringHelper.isItEmail(email));
        assertEquals(false, StringHelper.isItEmail(phonenumber));
    }

    @Test
    public void latinCharacterValidationTest() {
        String latin = "ABCDEFGHJIKLMNOPQRSTUVXYZ";
        String persian = "پویا";

        assertEquals(true, StringHelper.isValidISOLatin1(latin));
        assertEquals(false, StringHelper.isValidISOLatin1(persian));
    }

    @Test
    public void extractingZarinpalDescriptionTest() {
//        newDescTest("پرداخت کننده: Pouya Amirahmadi\nتوضیحات: ");
//        newDescTest("پرداخت کننده: s3m.games\nتوضیحات: TOIH");
//        newDescTest("پرداخت کننده: s3m.games\nتوضیحات: سلام چطوری");
    }
    @Test
    public void extractingZarinpalDescriptionNameTest() {
//        newNameTest("پرداخت کننده: بنده خدا \nتوضیحات: ", "Guest");
//        newNameTest("پرداخت کننده: s3m games\nتوضیحات: TOIH", "s3m.games");
//        newNameTest("پرداخت کننده: s3m.games\nتوضیحات: سلام چطوری", "s3m.games");
    }

    @Test
    public void extractingZarinpalDescriptionTestInvalid() {
//        newInvalidDescTest(null);
//        newInvalidDescTest("");
//        newInvalidDescTest("KLASJDKAJSD");
//        newInvalidDescTest("پرداخت : s3m.games\nتوضیحات: سلام چطوری");
//        newInvalidDescTest("پرداخت کننده: s3m.gamesتوضیحات: سلام چطوری");
    }

    private void newDescTest(String description) {
        HashMap<String, String> extracted = StringHelper.extractDataFromZarinpalDesc(description);
        assertEquals(Constant.PAYER_KEY, extracted.keySet().toArray()[0]);
        assertEquals(Constant.DESC_KEY, extracted.keySet().toArray()[1]);
        assertEquals(true, (extracted.containsKey(Constant.PAYER_KEY) && extracted.containsKey(Constant.DESC_KEY)));
    }
    private void newInvalidDescTest(String description) {
        HashMap<String, String> extracted = StringHelper.extractDataFromZarinpalDesc(description);
        assertEquals(false, (extracted.containsKey(Constant.PAYER_KEY) && extracted.containsKey(Constant.DESC_KEY)));
    }

    private void newNameTest(String description, String name) {
        HashMap<String, String> extracted = StringHelper.extractDataFromZarinpalDesc(description);
        assertEquals(true, extracted.get(Constant.PAYER_KEY).contains(name));
    }
}