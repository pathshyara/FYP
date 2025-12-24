package com.example.backend.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/**
 * PinyinService provides accurate pinyin pronunciation for Chinese characters and words.
 * It uses a comprehensive database of pinyin mappings with tone marks.
 * 
 * The service handles:
 * - Single character pinyin lookup
 * - Multi-character word pinyin conversion
 * - Tone mark preservation (ā á ǎ à, ē é ě è, etc.)
 * - Common Malay-to-Mandarin word mappings with accurate pinyin
 */
@Service
public class PinyinService {
    
    private static final Logger logger = LoggerFactory.getLogger(PinyinService.class);
    
    // Comprehensive database of pinyin for Chinese characters used in common words
    private static final Map<String, String> CHARACTER_PINYIN = new HashMap<>();
    
    // Pre-compiled mappings for common Malay words to their Mandarin equivalents with pinyin
    private static final Map<String, String> WORD_PINYIN_MAPPING = new HashMap<>();
    
    static {
        // Initialize character-level pinyin database
        initializeCharacterDatabase();
        
        // Initialize common word mappings
        initializeWordMappings();
    }
    
    private static void initializeCharacterDatabase() {
        // Common characters for common words
        CHARACTER_PINYIN.put("睡", "shuì");
        CHARACTER_PINYIN.put("觉", "jiào");
        CHARACTER_PINYIN.put("吃", "chī");
        CHARACTER_PINYIN.put("饭", "fàn");
        CHARACTER_PINYIN.put("美", "měi");
        CHARACTER_PINYIN.put("丽", "lì");
        CHARACTER_PINYIN.put("害", "hài");
        CHARACTER_PINYIN.put("怕", "pà");
        CHARACTER_PINYIN.put("枯", "kū");
        CHARACTER_PINYIN.put("萎", "wěi");
        CHARACTER_PINYIN.put("忧", "yōu");
        CHARACTER_PINYIN.put("郁", "yù");
        CHARACTER_PINYIN.put("学", "xué");
        CHARACTER_PINYIN.put("习", "xí");
        CHARACTER_PINYIN.put("真", "zhēn");
        CHARACTER_PINYIN.put("有", "yǒu");
        CHARACTER_PINYIN.put("趣", "qù");
        CHARACTER_PINYIN.put("观", "guān");
        CHARACTER_PINYIN.put("点", "diǎn");
        CHARACTER_PINYIN.put("看", "kàn");
        CHARACTER_PINYIN.put("的", "de");
        CHARACTER_PINYIN.put("是", "shì");
        CHARACTER_PINYIN.put("一", "yī");
        CHARACTER_PINYIN.put("在", "zài");
        CHARACTER_PINYIN.put("冷", "lěng");
        CHARACTER_PINYIN.put("京", "jīng");
        CHARACTER_PINYIN.put("快", "kuài");
        CHARACTER_PINYIN.put("乐", "lè");
        CHARACTER_PINYIN.put("凉", "liáng");
        CHARACTER_PINYIN.put("伤", "shāng");
        CHARACTER_PINYIN.put("心", "xīn");
        CHARACTER_PINYIN.put("开", "kāi");
        CHARACTER_PINYIN.put("生", "shēng");
        CHARACTER_PINYIN.put("气", "qì");
        CHARACTER_PINYIN.put("爱", "ài");
        CHARACTER_PINYIN.put("想", "xiǎng");
        CHARACTER_PINYIN.put("念", "niàn");
        
        // Additional common characters for other words
        CHARACTER_PINYIN.put("我", "wǒ");
        CHARACTER_PINYIN.put("你", "nǐ");
        CHARACTER_PINYIN.put("他", "tā");
        CHARACTER_PINYIN.put("她", "tā");
        CHARACTER_PINYIN.put("是", "shì");
        CHARACTER_PINYIN.put("有", "yǒu");
        CHARACTER_PINYIN.put("很", "hěn");
        CHARACTER_PINYIN.put("好", "hǎo");
        CHARACTER_PINYIN.put("大", "dà");
        CHARACTER_PINYIN.put("小", "xiǎo");
        CHARACTER_PINYIN.put("高", "gāo");
        CHARACTER_PINYIN.put("快", "kuài");
        CHARACTER_PINYIN.put("慢", "màn");
        CHARACTER_PINYIN.put("新", "xīn");
        CHARACTER_PINYIN.put("老", "lǎo");
        CHARACTER_PINYIN.put("多", "duō");
        CHARACTER_PINYIN.put("少", "shǎo");
        CHARACTER_PINYIN.put("长", "cháng");
        CHARACTER_PINYIN.put("短", "duǎn");
        CHARACTER_PINYIN.put("白", "bái");
        CHARACTER_PINYIN.put("黑", "hēi");
        CHARACTER_PINYIN.put("红", "hóng");
        CHARACTER_PINYIN.put("蓝", "lán");
        CHARACTER_PINYIN.put("绿", "lǜ");
        CHARACTER_PINYIN.put("黄", "huáng");
        CHARACTER_PINYIN.put("水", "shuǐ");
        CHARACTER_PINYIN.put("火", "huǒ");
        CHARACTER_PINYIN.put("土", "tǔ");
        CHARACTER_PINYIN.put("木", "mù");
        CHARACTER_PINYIN.put("金", "jīn");
        CHARACTER_PINYIN.put("书", "shū");
        CHARACTER_PINYIN.put("笔", "bǐ");
        CHARACTER_PINYIN.put("纸", "zhǐ");
        CHARACTER_PINYIN.put("桌", "zhuō");
        CHARACTER_PINYIN.put("椅", "yǐ");
        CHARACTER_PINYIN.put("门", "mén");
        CHARACTER_PINYIN.put("窗", "chuāng");
        CHARACTER_PINYIN.put("房", "fáng");
        CHARACTER_PINYIN.put("间", "jiān");
        CHARACTER_PINYIN.put("天", "tiān");
        CHARACTER_PINYIN.put("地", "dì");
        CHARACTER_PINYIN.put("人", "rén");
        CHARACTER_PINYIN.put("口", "kǒu");
        CHARACTER_PINYIN.put("手", "shǒu");
        CHARACTER_PINYIN.put("脚", "jiǎo");
        CHARACTER_PINYIN.put("头", "tóu");
        CHARACTER_PINYIN.put("眼", "yǎn");
        CHARACTER_PINYIN.put("耳", "ěr");
        CHARACTER_PINYIN.put("鼻", "bí");
        CHARACTER_PINYIN.put("嘴", "zuǐ");
        CHARACTER_PINYIN.put("心", "xīn");
        CHARACTER_PINYIN.put("肺", "fèi");
        CHARACTER_PINYIN.put("肝", "gān");
        CHARACTER_PINYIN.put("血", "xiě");
        CHARACTER_PINYIN.put("肉", "ròu");
        CHARACTER_PINYIN.put("骨", "gǔ");
        CHARACTER_PINYIN.put("皮", "pí");
        CHARACTER_PINYIN.put("肤", "fū");
        CHARACTER_PINYIN.put("病", "bìng");
        CHARACTER_PINYIN.put("痛", "ténɡ");
        CHARACTER_PINYIN.put("药", "yào");
        CHARACTER_PINYIN.put("医", "yī");
        CHARACTER_PINYIN.put("生", "shēng");
        CHARACTER_PINYIN.put("死", "sǐ");
        CHARACTER_PINYIN.put("走", "zǒu");
        CHARACTER_PINYIN.put("跑", "pǎo");
        CHARACTER_PINYIN.put("跳", "tiào");
        CHARACTER_PINYIN.put("坐", "zuò");
        CHARACTER_PINYIN.put("站", "zhàn");
        CHARACTER_PINYIN.put("躺", "tǎng");
        CHARACTER_PINYIN.put("说", "shuō");
        CHARACTER_PINYIN.put("听", "tīng");
        CHARACTER_PINYIN.put("看", "kàn");
        CHARACTER_PINYIN.put("读", "dú");
        CHARACTER_PINYIN.put("写", "xiě");
        CHARACTER_PINYIN.put("想", "xiǎng");
        CHARACTER_PINYIN.put("知", "zhī");
        CHARACTER_PINYIN.put("做", "zuò");
        CHARACTER_PINYIN.put("给", "gěi");
        CHARACTER_PINYIN.put("来", "lái");
        CHARACTER_PINYIN.put("去", "qù");
        CHARACTER_PINYIN.put("到", "dào");
        CHARACTER_PINYIN.put("在", "zài");
        CHARACTER_PINYIN.put("从", "cóng");
        CHARACTER_PINYIN.put("向", "xiàng");
        CHARACTER_PINYIN.put("让", "ràng");
        CHARACTER_PINYIN.put("问", "wèn");
        CHARACTER_PINYIN.put("答", "dá");
        CHARACTER_PINYIN.put("对", "duì");
        CHARACTER_PINYIN.put("错", "cuò");
        CHARACTER_PINYIN.put("同", "tóng");
        CHARACTER_PINYIN.put("异", "yì");
        CHARACTER_PINYIN.put("强", "qiáng");
        CHARACTER_PINYIN.put("弱", "ruò");
        CHARACTER_PINYIN.put("智", "zhì");
        CHARACTER_PINYIN.put("愚", "yú");
        CHARACTER_PINYIN.put("贫", "pín");
        CHARACTER_PINYIN.put("富", "fù");
        CHARACTER_PINYIN.put("贵", "guì");
        CHARACTER_PINYIN.put("贱", "jiàn");
        CHARACTER_PINYIN.put("善", "shàn");
        CHARACTER_PINYIN.put("恶", "è");
        CHARACTER_PINYIN.put("真", "zhēn");
        CHARACTER_PINYIN.put("假", "jiǎ");
        CHARACTER_PINYIN.put("清", "qīng");
        CHARACTER_PINYIN.put("浊", "zhuó");
        CHARACTER_PINYIN.put("冷", "lěng");
        CHARACTER_PINYIN.put("热", "rè");
        CHARACTER_PINYIN.put("干", "gān");
        CHARACTER_PINYIN.put("湿", "shī");
        CHARACTER_PINYIN.put("硬", "yìng");
        CHARACTER_PINYIN.put("软", "ruǎn");
        CHARACTER_PINYIN.put("光", "guāng");
        CHARACTER_PINYIN.put("暗", "àn");
        CHARACTER_PINYIN.put("甜", "tián");
        CHARACTER_PINYIN.put("苦", "kǔ");
        CHARACTER_PINYIN.put("酸", "suān");
        CHARACTER_PINYIN.put("咸", "xián");
        CHARACTER_PINYIN.put("香", "xiāng");
        CHARACTER_PINYIN.put("臭", "chòu");
        CHARACTER_PINYIN.put("软", "ruǎn");
        CHARACTER_PINYIN.put("硬", "yìng");
        CHARACTER_PINYIN.put("滑", "huá");
        CHARACTER_PINYIN.put("浪", "làng");
        CHARACTER_PINYIN.put("费", "fèi");
        CHARACTER_PINYIN.put("粗", "cū");
        CHARACTER_PINYIN.put("细", "xì");
        CHARACTER_PINYIN.put("光", "guāng");
        CHARACTER_PINYIN.put("暗", "àn");
        CHARACTER_PINYIN.put("亮", "liàng");
        CHARACTER_PINYIN.put("暗", "àn");
        CHARACTER_PINYIN.put("清", "qīng");
        CHARACTER_PINYIN.put("浑", "húa");
        CHARACTER_PINYIN.put("乱", "luàn");
        CHARACTER_PINYIN.put("整", "zhěng");
        CHARACTER_PINYIN.put("圆", "yuán");
        CHARACTER_PINYIN.put("方", "fāng");
        CHARACTER_PINYIN.put("尖", "jiān");
        CHARACTER_PINYIN.put("钝", "dùn");
        CHARACTER_PINYIN.put("厚", "hòu");
        CHARACTER_PINYIN.put("薄", "báo");
        CHARACTER_PINYIN.put("深", "shēn");
        CHARACTER_PINYIN.put("浅", "qiǎn");
        CHARACTER_PINYIN.put("高", "gāo");
        CHARACTER_PINYIN.put("低", "dī");
        CHARACTER_PINYIN.put("宽", "kuān");
        CHARACTER_PINYIN.put("窄", "zhǎi");
        CHARACTER_PINYIN.put("远", "yuǎn");
        CHARACTER_PINYIN.put("近", "jìn");
        CHARACTER_PINYIN.put("前", "qián");
        CHARACTER_PINYIN.put("后", "hòu");
        CHARACTER_PINYIN.put("左", "zuǒ");
        CHARACTER_PINYIN.put("右", "yòu");
        CHARACTER_PINYIN.put("上", "shàng");
        CHARACTER_PINYIN.put("下", "xià");
        CHARACTER_PINYIN.put("中", "zhōng");
        CHARACTER_PINYIN.put("内", "nèi");
        CHARACTER_PINYIN.put("外", "wài");
        CHARACTER_PINYIN.put("里", "lǐ");
        CHARACTER_PINYIN.put("家", "jiā");
        CHARACTER_PINYIN.put("国", "guó");
        CHARACTER_PINYIN.put("城", "chéng");
        CHARACTER_PINYIN.put("村", "cūn");
        CHARACTER_PINYIN.put("街", "jiē");
        CHARACTER_PINYIN.put("巷", "xiàng");
        CHARACTER_PINYIN.put("山", "shān");
        CHARACTER_PINYIN.put("水", "shuǐ");
        CHARACTER_PINYIN.put("河", "hé");
        CHARACTER_PINYIN.put("海", "hǎi");
        CHARACTER_PINYIN.put("湖", "hú");
        CHARACTER_PINYIN.put("池", "chí");
        CHARACTER_PINYIN.put("井", "jǐng");
        CHARACTER_PINYIN.put("泉", "quán");
        CHARACTER_PINYIN.put("沙", "shā");
        CHARACTER_PINYIN.put("石", "shí");
        CHARACTER_PINYIN.put("土", "tǔ");
        CHARACTER_PINYIN.put("尘", "chén");
        CHARACTER_PINYIN.put("灰", "huī");
        CHARACTER_PINYIN.put("雨", "yǔ");
        CHARACTER_PINYIN.put("雪", "xuě");
        CHARACTER_PINYIN.put("云", "yún");
        CHARACTER_PINYIN.put("雾", "wù");
        CHARACTER_PINYIN.put("冰", "bīng");
        CHARACTER_PINYIN.put("霜", "shuāng");
        CHARACTER_PINYIN.put("露", "lù");
        CHARACTER_PINYIN.put("风", "fēng");
        CHARACTER_PINYIN.put("雷", "léi");
        CHARACTER_PINYIN.put("电", "diàn");
        CHARACTER_PINYIN.put("闪", "shǎn");
        CHARACTER_PINYIN.put("雹", "báo");
        CHARACTER_PINYIN.put("月", "yuè");
        CHARACTER_PINYIN.put("星", "xīng");
        CHARACTER_PINYIN.put("日", "rì");
        CHARACTER_PINYIN.put("晨", "chén");
        CHARACTER_PINYIN.put("午", "wǔ");
        CHARACTER_PINYIN.put("晚", "wǎn");
        CHARACTER_PINYIN.put("夜", "yè");
        CHARACTER_PINYIN.put("春", "chūn");
        CHARACTER_PINYIN.put("夏", "xià");
        CHARACTER_PINYIN.put("秋", "qiū");
        CHARACTER_PINYIN.put("冬", "dōng");
        CHARACTER_PINYIN.put("年", "nián");
        CHARACTER_PINYIN.put("月", "yuè");
        CHARACTER_PINYIN.put("日", "rì");
        CHARACTER_PINYIN.put("周", "zhōu");
        CHARACTER_PINYIN.put("时", "shí");
        CHARACTER_PINYIN.put("分", "fēn");
        CHARACTER_PINYIN.put("秒", "miǎo");
        CHARACTER_PINYIN.put("一", "yī");
        CHARACTER_PINYIN.put("二", "èr");
        CHARACTER_PINYIN.put("三", "sān");
        CHARACTER_PINYIN.put("四", "sì");
        CHARACTER_PINYIN.put("五", "wǔ");
        CHARACTER_PINYIN.put("六", "liù");
        CHARACTER_PINYIN.put("七", "qī");
        CHARACTER_PINYIN.put("八", "bā");
        CHARACTER_PINYIN.put("九", "jiǔ");
        CHARACTER_PINYIN.put("十", "shí");
        CHARACTER_PINYIN.put("百", "bǎi");
        CHARACTER_PINYIN.put("千", "qiān");
        CHARACTER_PINYIN.put("万", "wàn");
        CHARACTER_PINYIN.put("亿", "yì");
        CHARACTER_PINYIN.put("今", "jīn");
        CHARACTER_PINYIN.put("昨", "zuó");
        CHARACTER_PINYIN.put("明", "míng");
        CHARACTER_PINYIN.put("刚", "gāng");
        CHARACTER_PINYIN.put("才", "cái");
        CHARACTER_PINYIN.put("还", "háo");
        CHARACTER_PINYIN.put("又", "yòu");
        CHARACTER_PINYIN.put("就", "jiù");
        CHARACTER_PINYIN.put("便", "biàn");
        CHARACTER_PINYIN.put("然", "rán");
        CHARACTER_PINYIN.put("而", "ér");
        CHARACTER_PINYIN.put("但", "dàn");
        CHARACTER_PINYIN.put("或", "huò");
        CHARACTER_PINYIN.put("及", "jí");
        CHARACTER_PINYIN.put("与", "yǔ");
        CHARACTER_PINYIN.put("非", "fēi");
        CHARACTER_PINYIN.put("否", "fǒu");
        CHARACTER_PINYIN.put("此", "cǐ");
        CHARACTER_PINYIN.put("彼", "bǐ");
        CHARACTER_PINYIN.put("何", "hé");
        CHARACTER_PINYIN.put("谁", "shuí");
        CHARACTER_PINYIN.put("哪", "nǎ");
        CHARACTER_PINYIN.put("那", "nà");
        CHARACTER_PINYIN.put("这", "zhè");
        CHARACTER_PINYIN.put("个", "gè");
        CHARACTER_PINYIN.put("都", "dōu");
        CHARACTER_PINYIN.put("很", "hěn");
        CHARACTER_PINYIN.put("太", "tài");
        CHARACTER_PINYIN.put("最", "zuì");
        CHARACTER_PINYIN.put("比", "bǐ");
        CHARACTER_PINYIN.put("较", "jiào");
        CHARACTER_PINYIN.put("更", "gèng");
        CHARACTER_PINYIN.put("又", "yòu");
        CHARACTER_PINYIN.put("或", "huò");
        CHARACTER_PINYIN.put("及", "jí");
    }
    
    private static void initializeWordMappings() {
        // Common Malay words with their accurate pinyin pronunciations
        WORD_PINYIN_MAPPING.put("makan", "chī fàn");
        WORD_PINYIN_MAPPING.put("tidur", "shuì jiào");
        WORD_PINYIN_MAPPING.put("cantik", "měi lì");
        WORD_PINYIN_MAPPING.put("gerun", "hài pà");
        WORD_PINYIN_MAPPING.put("layu", "kū wěi");
        WORD_PINYIN_MAPPING.put("muram", "yōu yù");
        WORD_PINYIN_MAPPING.put("dingin", "lěng");
        WORD_PINYIN_MAPPING.put("kelajar", "xué xí");
        WORD_PINYIN_MAPPING.put("belajar", "xué xí");
        WORD_PINYIN_MAPPING.put("gembira", "kuài lè");
        WORD_PINYIN_MAPPING.put("sedih", "shāng xīn");
        WORD_PINYIN_MAPPING.put("marah", "shēng qì");
        WORD_PINYIN_MAPPING.put("cinta", "ài");
        WORD_PINYIN_MAPPING.put("rindu", "xiǎng niàn");
        WORD_PINYIN_MAPPING.put("bulat", "yuán xíng");
        WORD_PINYIN_MAPPING.put("bujur", "Tuǒyuán xíng");
        
        // Chinese words to pinyin (for when looking up Chinese characters)
        WORD_PINYIN_MAPPING.put("开心", "kāi xīn");
        WORD_PINYIN_MAPPING.put("伤心", "shāng xīn");
        WORD_PINYIN_MAPPING.put("生气", "shēng qì");
        WORD_PINYIN_MAPPING.put("爱", "ài");
        WORD_PINYIN_MAPPING.put("想念", "xiǎng niàn");
        WORD_PINYIN_MAPPING.put("聪明", "cōngmíng");
        WORD_PINYIN_MAPPING.put("美丽", "měi lì");
        WORD_PINYIN_MAPPING.put("害怕", "hài pà");
        WORD_PINYIN_MAPPING.put("快乐", "kuài lè");
        WORD_PINYIN_MAPPING.put("快", "kuài");
        WORD_PINYIN_MAPPING.put("慢", "màn");
        WORD_PINYIN_MAPPING.put("高", "gāo");
        WORD_PINYIN_MAPPING.put("矮", "ǎi");
        WORD_PINYIN_MAPPING.put("好", "hǎo");
        WORD_PINYIN_MAPPING.put("枯萎", "kū wěi");
        WORD_PINYIN_MAPPING.put("圆形", "yuán xíng");
        WORD_PINYIN_MAPPING.put("椭圆形", "Tuǒyuán xíng");
        WORD_PINYIN_MAPPING.put("浪费", "làngfèi");
        
        WORD_PINYIN_MAPPING.put("saya", "wǒ");
        WORD_PINYIN_MAPPING.put("anda", "nǐ");
        WORD_PINYIN_MAPPING.put("dia", "tā");
        WORD_PINYIN_MAPPING.put("baik", "hǎo");
        WORD_PINYIN_MAPPING.put("besar", "dà");
        WORD_PINYIN_MAPPING.put("kecil", "xiǎo");
        WORD_PINYIN_MAPPING.put("tinggi", "gāo");
        WORD_PINYIN_MAPPING.put("rendah", "dī");
        WORD_PINYIN_MAPPING.put("cepat", "kuài");
        WORD_PINYIN_MAPPING.put("lambat", "màn");
        WORD_PINYIN_MAPPING.put("baru", "xīn");
        WORD_PINYIN_MAPPING.put("tua", "lǎo");
        WORD_PINYIN_MAPPING.put("banyak", "duō");
        WORD_PINYIN_MAPPING.put("sedikit", "shǎo");
        WORD_PINYIN_MAPPING.put("panjang", "cháng");
        WORD_PINYIN_MAPPING.put("pendek", "duǎn");
        WORD_PINYIN_MAPPING.put("putih", "bái");
        WORD_PINYIN_MAPPING.put("hitam", "hēi");
        WORD_PINYIN_MAPPING.put("merah", "hóng");
        WORD_PINYIN_MAPPING.put("kuning", "huáng");
        WORD_PINYIN_MAPPING.put("hijau", "lǜ");
        WORD_PINYIN_MAPPING.put("biru", "lán");
        WORD_PINYIN_MAPPING.put("ungu", "zǐ");
        WORD_PINYIN_MAPPING.put("air", "shuǐ");
        WORD_PINYIN_MAPPING.put("api", "huǒ");
        WORD_PINYIN_MAPPING.put("angin", "fēng");
        WORD_PINYIN_MAPPING.put("tanah", "tǔ");
        WORD_PINYIN_MAPPING.put("rumah", "fáng");
        WORD_PINYIN_MAPPING.put("sekolah", "xuéxiào");
        WORD_PINYIN_MAPPING.put("kota", "chéng");
        WORD_PINYIN_MAPPING.put("kampung", "cūn");
        WORD_PINYIN_MAPPING.put("jalan", "lù");
        WORD_PINYIN_MAPPING.put("gunung", "shān");
        WORD_PINYIN_MAPPING.put("lembah", "gǔ");
        WORD_PINYIN_MAPPING.put("sungai", "hé");
        WORD_PINYIN_MAPPING.put("laut", "hǎi");
        WORD_PINYIN_MAPPING.put("danau", "hú");
        WORD_PINYIN_MAPPING.put("mata", "yǎn");
        WORD_PINYIN_MAPPING.put("telinga", "ěr");
        WORD_PINYIN_MAPPING.put("hidung", "bí");
        WORD_PINYIN_MAPPING.put("mulut", "zuǐ");
        WORD_PINYIN_MAPPING.put("gigi", "chǐ");
        WORD_PINYIN_MAPPING.put("tangan", "shǒu");
        WORD_PINYIN_MAPPING.put("kaki", "jiǎo");
        WORD_PINYIN_MAPPING.put("kepala", "tóu");
        WORD_PINYIN_MAPPING.put("hati", "xīn");
        WORD_PINYIN_MAPPING.put("perut", "dù");
        WORD_PINYIN_MAPPING.put("dada", "xiōng");
        WORD_PINYIN_MAPPING.put("punggung", "bèi");
        WORD_PINYIN_MAPPING.put("lengan", "bì");
        WORD_PINYIN_MAPPING.put("paha", "tuǐ");
        WORD_PINYIN_MAPPING.put("betis", "xiǎo tuǐ");
        WORD_PINYIN_MAPPING.put("kuku", "zhǐ jia");
        WORD_PINYIN_MAPPING.put("rambut", "tóu fa");
        WORD_PINYIN_MAPPING.put("kulit", "pí fu");
        WORD_PINYIN_MAPPING.put("darah", "xiě");
        WORD_PINYIN_MAPPING.put("daging", "ròu");
        WORD_PINYIN_MAPPING.put("tulang", "gǔ");
        WORD_PINYIN_MAPPING.put("sakit", "bìng");
        WORD_PINYIN_MAPPING.put("sembuh", "hǎo le");
        WORD_PINYIN_MAPPING.put("ubat", "yào");
        WORD_PINYIN_MAPPING.put("doktor", "yī shēng");
        WORD_PINYIN_MAPPING.put("rumah sakit", "yī yuàn");
        WORD_PINYIN_MAPPING.put("jalan", "zǒu");
        WORD_PINYIN_MAPPING.put("lari", "pǎo");
        WORD_PINYIN_MAPPING.put("melompat", "tiào");
        WORD_PINYIN_MAPPING.put("duduk", "zuò");
        WORD_PINYIN_MAPPING.put("berdiri", "zhàn");
        WORD_PINYIN_MAPPING.put("berbaring", "tǎng");
        WORD_PINYIN_MAPPING.put("terbang", "fēi");
        WORD_PINYIN_MAPPING.put("berenang", "yóu yǒng");
        WORD_PINYIN_MAPPING.put("menari", "tiào wǔ");
        WORD_PINYIN_MAPPING.put("bernyanyi", "chàng gē");
        WORD_PINYIN_MAPPING.put("menyanyi", "chàng gē");
        WORD_PINYIN_MAPPING.put("bermain", "wán");
        WORD_PINYIN_MAPPING.put("bekerja", "gōng zuò");
        WORD_PINYIN_MAPPING.put("istirahat", "xiū xi");
        WORD_PINYIN_MAPPING.put("istirahat", "xiū xi");
        WORD_PINYIN_MAPPING.put("mengajar", "jiāo shū");
        WORD_PINYIN_MAPPING.put("membaca", "dú shū");
        WORD_PINYIN_MAPPING.put("menulis", "xiě zì");
        WORD_PINYIN_MAPPING.put("menghitung", "suàn");
        WORD_PINYIN_MAPPING.put("berpikir", "xiǎng");
        WORD_PINYIN_MAPPING.put("mendengarkan", "tīng");
        WORD_PINYIN_MAPPING.put("berbicara", "shuō huà");
        WORD_PINYIN_MAPPING.put("bertanya", "wèn");
        WORD_PINYIN_MAPPING.put("menjawab", "huí dá");
        WORD_PINYIN_MAPPING.put("tahu", "zhī dao");
        WORD_PINYIN_MAPPING.put("lupa", "wàng jì");
        WORD_PINYIN_MAPPING.put("ingat", "jì de");
        WORD_PINYIN_MAPPING.put("percaya", "xiāng xìn");
        WORD_PINYIN_MAPPING.put("ragu", "huái yí");
        WORD_PINYIN_MAPPING.put("suka", "xǐ huan");
        WORD_PINYIN_MAPPING.put("benci", "tǎo yàn");
        WORD_PINYIN_MAPPING.put("senang", "gaoxìng");
        WORD_PINYIN_MAPPING.put("sedih", "bēi shāng");
        WORD_PINYIN_MAPPING.put("marah", "shēngqì");
        WORD_PINYIN_MAPPING.put("takut", "hài pa");
        WORD_PINYIN_MAPPING.put("berani", "yǒng gǎn");
        WORD_PINYIN_MAPPING.put("malu", "xiū chǐ");
        WORD_PINYIN_MAPPING.put("bangga", "jiāo ao");
        WORD_PINYIN_MAPPING.put("cinta", "ài");
        WORD_PINYIN_MAPPING.put("benci", "tǎo yàn");
        WORD_PINYIN_MAPPING.put("cucu", "sun");
        WORD_PINYIN_MAPPING.put("nenek", "né nai");
        WORD_PINYIN_MAPPING.put("kakek", "yé ye");
        WORD_PINYIN_MAPPING.put("ayah", "bà ba");
        WORD_PINYIN_MAPPING.put("ibu", "mā ma");
        WORD_PINYIN_MAPPING.put("adik", "di di");
        WORD_PINYIN_MAPPING.put("kakak", "jiě jie");
        WORD_PINYIN_MAPPING.put("suami", "zhàng fu");
        WORD_PINYIN_MAPPING.put("istri", "qī zǐ");
        WORD_PINYIN_MAPPING.put("anak", "háizi");
        WORD_PINYIN_MAPPING.put("bayi", "yíng ér");
        WORD_PINYIN_MAPPING.put("anak laki", "ér zǐ");
        WORD_PINYIN_MAPPING.put("anak perempuan", "nǚ ér");
        WORD_PINYIN_MAPPING.put("kawan", "péngyou");
        WORD_PINYIN_MAPPING.put("musuh", "dí ren");
        WORD_PINYIN_MAPPING.put("guru", "lǎo shī");
        WORD_PINYIN_MAPPING.put("siswa", "xué shēng");
        WORD_PINYIN_MAPPING.put("dokter", "yī shēng");
        WORD_PINYIN_MAPPING.put("perawat", "hù shi");
        WORD_PINYIN_MAPPING.put("petani", "nóng mín");
        WORD_PINYIN_MAPPING.put("nelayan", "yú mín");
        WORD_PINYIN_MAPPING.put("pedagang", "shāng rén");
        WORD_PINYIN_MAPPING.put("tukang", "gōng rén");
        WORD_PINYIN_MAPPING.put("tentara", "jūn rén");
        WORD_PINYIN_MAPPING.put("polisi", "jǐng chá");
    }
    
    /**
     * Get pinyin for a word from the predefined mapping
     * If not found, uses character-by-character conversion
     * 
     * @param word Chinese word to convert (supports Chinese characters)
     * @return Pinyin with tone marks
     */
    public String getPinyin(String word) {
        if (word == null || word.isEmpty()) {
            logger.warn("Received null or empty word for pinyin conversion");
            return "No pinyin available";
        }
        
        // Check if we have a direct word mapping first
        if (WORD_PINYIN_MAPPING.containsKey(word.toLowerCase())) {
            String pinyin = WORD_PINYIN_MAPPING.get(word.toLowerCase());
            logger.debug("Found word mapping for '{}': {}", word, pinyin);
            return pinyin;
        }
        
        // If it's a Malay word (not Chinese characters), try character-level conversion
        // This handles Chinese characters that were passed
        try {
            StringBuilder result = new StringBuilder();
            boolean hasChineseChars = false;
            
            for (char ch : word.toCharArray()) {
                String charStr = String.valueOf(ch);
                
                if (CHARACTER_PINYIN.containsKey(charStr)) {
                    if (result.length() > 0) {
                        result.append(" ");
                    }
                    result.append(CHARACTER_PINYIN.get(charStr));
                    hasChineseChars = true;
                } else if (hasChineseChars) {
                    // We found Chinese characters but this one isn't in our database
                    logger.debug("Character '{}' not found in pinyin database, using fallback", ch);
                    result.append(" [?]");
                }
            }
            
            if (hasChineseChars && result.length() > 0) {
                String pinyin = result.toString().replaceAll("  +", " ").trim();
                logger.debug("Generated pinyin for Chinese characters '{}': {}", word, pinyin);
                return pinyin;
            }
        } catch (Exception e) {
            logger.error("Error processing word '{}' for pinyin conversion: {}", word, e.getMessage());
        }
        
        // Fallback: return the word as-is with a note
        logger.warn("Could not generate pinyin for word '{}', no Chinese characters found", word);
        return "Pinyin not available for '" + word + "'";
    }
    
    /**
     * Check if a word has a pinyin mapping
     * 
     * @param word The word to check
     * @return true if pinyin is available, false otherwise
     */
    public boolean hasPinyinMapping(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        return WORD_PINYIN_MAPPING.containsKey(word.toLowerCase());
    }
    
    /**
     * Get all available word mappings (for testing/debugging)
     * 
     * @return Map of word to pinyin
     */
    public Map<String, String> getAllMappings() {
        return new HashMap<>(WORD_PINYIN_MAPPING);
    }
    
    /**
     * Add or update a word mapping
     * 
     * @param word   The word to map
     * @param pinyin The pinyin pronunciation
     */
    public void addMapping(String word, String pinyin) {
        if (word != null && !word.isEmpty() && pinyin != null && !pinyin.isEmpty()) {
            WORD_PINYIN_MAPPING.put(word.toLowerCase(), pinyin);
            logger.info("Added pinyin mapping: {} -> {}", word, pinyin);
        }
    }
}
