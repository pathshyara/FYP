package com.example.backend.service;

import com.example.backend.model.AiResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Enhanced DeepSeekAiService that overrides specific word translations
 * for better handling of common Malay words.
 * 
 * NOTE: @Primary annotation enabled to use enhanced translations
 */
@Service
@Profile("dev")
@Primary
public class EnhancedDeepseekAiService extends DeepseekAiService {

    private static final Logger logger = LoggerFactory.getLogger(EnhancedDeepseekAiService.class);

    public EnhancedDeepseekAiService(WebClient webClient, PinyinService pinyinService) {
        super(webClient, pinyinService);
        logger.info("EnhancedDeepseekAiService initialized - providing enhanced translations for common Malay words");
    }

    @Override
    public Mono<AiResponse> generateExplanation(String word, String language) {
        String lowercaseWord = word.toLowerCase();

        // Enhanced prompt for specific Malay words
        Mono<AiResponse> response = super.generateExplanation(word, language);

        // Post-process the response based on specific words
        switch (lowercaseWord) {
            case "layu":
            case "枯萎":  // Chinese equivalent
                response = response.map(this::enhanceLayu);
                break;
            case "gerun":
            case "害怕":  // Chinese equivalent
                response = response.map(this::enhanceGerun);
                break;
            case "cantik":
            case "美丽":  // Chinese equivalent
                response = response.map(this::enhanceCantik);
                break;
            case "pintar":
            case "聪明":  // Chinese equivalent
                response = response.map(this::enhancePintar);
                break;
            case "cepat":
            case "快":  // Chinese equivalent
                response = response.map(this::enhanceCepat);
                break;
            case "lambat":
            case "慢":  // Chinese equivalent
                response = response.map(this::enhanceLambat);
                break;
            case "tinggi":
            case "高":  // Chinese equivalent
                response = response.map(this::enhanceTinggi);
                break;
            case "pendek":
            case "矮":  // Chinese equivalent
                response = response.map(this::enhancePendek);
                break;
            case "baik":
            case "好":  // Chinese equivalent
                response = response.map(this::enhanceBaik);
                break;
            case "marah":
            case "生气":  // Chinese equivalent
                response = response.map(this::enhanceMarah);
                break;
            case "gembira":
            case "快乐":  // Chinese equivalent
                response = response.map(this::enhanceGembira);
                break;
            case "sedih":
            case "伤心":  // Chinese equivalent
            case "悲伤":  // Alternative Chinese
                response = response.map(this::enhanceSedih);
                break;
            case "bulat":
            case "圆形":  // Chinese equivalent
                response = response.map(this::enhanceBulat);
                break;
            case "bujur":
            case "椭圆形":  // Chinese equivalent
                response = response.map(this::enhanceBujur);
                break;
            case "浪费":  // Chinese word
                response = response.map(this::enhanceLangfei);
                break;
        }

        return response;
    }

    /**
     * Enhance the response for "layu" (withered/wilted)
     */
    private AiResponse enhanceLayu(AiResponse response) {
        logger.info("Enhancing translation for word 'layu' / 枯萎");

        response.setPronunciation("kū wěi");
        response.setExplanation(
                "枯萎 bermaksud layu atau melayu. Ia menggambarkan keadaan tumbuhan yang telah kehilangan kesegaran dan menjadi kering serta tidak segar lagi.");
        response.setExamples(
                "1. 花朵因缺水而枯萎了。\n" +
                        "   Bunga itu layu kerana kekurangan air.\n" +
                        "2. 不要让植物在阳光下枯萎。\n" +
                        "   Jangan biarkan tumbuhan layu di bawah cahaya matahari.\n" +
                        "3. 这些蔬菜已经开始枯萎了。\n" +
                        "   Sayur-sayuran ini sudah mula layu.");
        return response;
    }

    /**
     * Enhance the response for "gerun" (afraid/fearful)
     */
    private AiResponse enhanceGerun(AiResponse response) {
        logger.info("Enhancing translation for word 'gerun' / 害怕");

        response.setPronunciation("hài pà");
        response.setExplanation(
                "害怕 bermaksud gerun atau takut. Ia menggambarkan emosi ketakutan atau kebimbangan yang dirasai seseorang ketika menghadapi sesuatu yang menakutkan atau berbahaya.");
        response.setExamples(
                "1. 孩子害怕黑暗。\n" +
                        "   Kanak-kanak gerun akan kegelapan.\n" +
                        "2. 他对高处感到害怕。\n" +
                        "   Dia berasa gerun terhadap tempat tinggi.\n" +
                        "3. 不要害怕尝试新事物。\n" +
                        "   Jangan gerun untuk mencuba perkara baru.");
        return response;
    }

    /**
     * Enhance the response for "cantik" (beautiful)
     */
    private AiResponse enhanceCantik(AiResponse response) {
        logger.info("Enhancing translation for word 'cantik' / 美丽");

        response.setPronunciation("měi lì");
        response.setExplanation(
                "美丽 bermaksud cantik atau menarik perhatian. Ia menggambarkan sesuatu yang indah atau mempunyai keindahan dari segi penampilan, seperti seorang gadis yang cantik atau pemandangan yang indah.");
        response.setExamples(
                "1. 她是个美丽的女孩。\n" +
                        "   Dia seorang gadis yang cantik.\n" +
                        "2. 这里的风景非常美丽。\n" +
                        "   Pemandangan di sini sangat cantik.\n" +
                        "3. 那朵花开得很美丽。\n" +
                        "   Bunga itu mekar dengan cantiknya.");
        response.setAdjective(true);
        return response;
    }

    /**
     * Enhance the response for "pintar" (smart)
     */
    private AiResponse enhancePintar(AiResponse response) {
        logger.info("Enhancing translation for word 'pintar' / 聪明");

        response.setPronunciation("cōngmíng");
        response.setExplanation(
                "聪明 bermaksud pintar atau bijak. Ia menggambarkan seseorang yang mempunyai kebolehan mental yang baik, dapat memahami dan mempelajari sesuatu dengan cepat serta menyelesaikan masalah dengan efektif.");
        response.setExamples(
                "1. 她是班上最聪明的学生。\n" +
                        "   Dia pelajar paling pintar dalam kelasnya.\n" +
                        "2. 这个孩子非常聪明，学东西很快。\n" +
                        "   Anak ini sangat pintar, dia cepat belajar.\n" +
                        "3. 你必须聪明地解决这个问题。\n" +
                        "   Anda mesti menyelesaikan masalah ini dengan cara yang pintar.");
        response.setAdjective(true);
        return response;
    }

    /**
     * Enhance the response for "cepat" (fast)
     */
    private AiResponse enhanceCepat(AiResponse response) {
        // Only enhance if there are issues with the response
        if (isResponseIncomplete(response)) {
            logger.info("Enhancing translation for word 'cepat'");

            response.setPronunciation("kuài");

            if (response.getExplanation().contains("No explanation available")) {
                response.setExplanation(
                        "'快' dalam bahasa Mandarin bermaksud bergerak atau berlaku dengan kelajuan yang tinggi, atau dalam masa yang singkat. Ia juga boleh merujuk kepada sesuatu yang cekap atau efisien dalam penggunaan masa.");
            }

            if (response.getExamples().contains("No examples available")) {
                response.setExamples(
                        "1. 他跑得很快。\n" +
                                "   Dia berlari dengan cepat.\n" +
                                "2. 请快点，我们要迟到了。\n" +
                                "   Tolong cepat sikit, kita akan terlambat.\n" +
                                "3. 这种方法比较快。\n" +
                                "   Cara ini lebih cepat.");
            }

            response.setAdjective(true);
        }
        return response;
    }

    /**
     * Enhance the response for "lambat" (slow)
     */
    private AiResponse enhanceLambat(AiResponse response) {
        // Only enhance if there are issues with the response
        if (isResponseIncomplete(response)) {
            logger.info("Enhancing translation for word 'lambat'");

            response.setPronunciation("màn");

            if (response.getExplanation().contains("No explanation available")) {
                response.setExplanation(
                        "'慢' dalam bahasa Mandarin bermaksud bergerak atau berlaku dengan kelajuan yang rendah, atau mengambil masa yang lebih panjang daripada biasa. Ia juga boleh menggambarkan seseorang yang tidak cepat dalam tindakan atau pemikiran.");
            }

            if (response.getExamples().contains("No examples available")) {
                response.setExamples(
                        "1. 他走路很慢。\n" +
                                "   Dia berjalan dengan lambat.\n" +
                                "2. 这个电脑运行得很慢。\n" +
                                "   Komputer ini beroperasi dengan lambat.\n" +
                                "3. 请慢慢说，我听不懂。\n" +
                                "   Tolong cakap dengan lebih lambat, saya tidak faham.");
            }

            response.setAdjective(true);
        }
        return response;
    }

    /**
     * Enhance the response for "tinggi" (tall/high)
     */
    private AiResponse enhanceTinggi(AiResponse response) {
        // Only enhance if there are issues with the response
        if (isResponseIncomplete(response)) {
            logger.info("Enhancing translation for word 'tinggi'");

            response.setPronunciation("gāo");

            if (response.getExplanation().contains("No explanation available")) {
                response.setExplanation(
                        "'高' dalam bahasa Mandarin merujuk kepada sesuatu yang mempunyai jarak yang jauh dari bawah ke atas, atau berada pada kedudukan yang lebih atas berbanding dengan tahap biasa. Ia boleh digunakan untuk menggambarkan ketinggian fizikal, tahap, atau darjah sesuatu.");
            }

            if (response.getExamples().contains("No examples available")) {
                response.setExamples(
                        "1. 他个子很高。\n" +
                                "   Dia sangat tinggi.\n" +
                                "2. 这座山非常高。\n" +
                                "   Gunung ini sangat tinggi.\n" +
                                "3. 这个城市的生活成本很高。\n" +
                                "   Kos kehidupan di bandar ini sangat tinggi.");
            }

            response.setAdjective(true);
        }
        return response;
    }

    /**
     * Enhance the response for "pendek" (short)
     */
    private AiResponse enhancePendek(AiResponse response) {
        // Only enhance if there are issues with the response
        if (isResponseIncomplete(response)) {
            logger.info("Enhancing translation for word 'pendek'");

            response.setPronunciation("ǎi");

            if (response.getExplanation().contains("No explanation available")) {
                response.setExplanation(
                        "'矮' dalam bahasa Mandarin merujuk kepada sesuatu yang mempunyai ketinggian yang rendah atau kurang daripada purata. Ia biasanya digunakan untuk menggambarkan ketinggian fizikal seseorang atau objek.");
            }

            if (response.getExamples().contains("No examples available")) {
                response.setExamples(
                        "1. 他比我矮一点。\n" +
                                "   Dia sedikit lebih pendek daripada saya.\n" +
                                "2. 那棵树很矮。\n" +
                                "   Pokok itu sangat pendek.\n" +
                                "3. 矮个子的人也可以打篮球。\n" +
                                "   Orang yang pendek juga boleh bermain bola keranjang.");
            }

            response.setAdjective(true);
        }
        return response;
    }

    /**
     * Enhance the response for "baik" (good/kind)
     */
    private AiResponse enhanceBaik(AiResponse response) {
        logger.info("Enhancing translation for word 'baik' / 好");

        response.setPronunciation("hǎo");
        response.setExplanation(
                "好 bermaksud baik atau bagus, memuaskan atau mempunyai kualiti yang tinggi. Ia juga boleh bermakna bersikap baik atau bersopan santun dalam konteks tingkah laku seseorang.");
        response.setExamples(
                "好 (good):\n" +
                        "好人 - orang baik (good person)\n" +
                        "好东西 - perkara baik (good thing)\n" +
                        "好朋友 - teman baik (good friend)\n" +
                        "好天气 - cuaca baik (good weather)\n" +
                        "好日子 - hari baik (good day)");
        response.setAdjective(true);
        return response;
    }

    /**
     * Enhance the response for "marah" (angry)
     */
    private AiResponse enhanceMarah(AiResponse response) {
        logger.info("Enhancing translation for word 'marah' / 生气");

        response.setPronunciation("shēng qì");
        response.setExplanation(
                "生气 bermaksud marah atau berang. Ia menggambarkan perasaan tidak senang atau tersinggung yang kuat, biasanya diikuti oleh kemarahan atau ketidakpuasan terhadap sesuatu.");
        response.setExamples(
                "1. 他对我生气了。\n" +
                        "   Dia marah kepada saya.\n" +
                        "2. 别生气，这不是你的错。\n" +
                        "   Jangan marah, ini bukan salah kamu.\n" +
                        "3. 她很容易生气。\n" +
                        "   Dia mudah marah.");
        response.setAdjective(true);
        return response;
    }

    /**
     * Enhance the response for "gembira" (happy)
     */
    private AiResponse enhanceGembira(AiResponse response) {
        logger.info("Enhancing translation for word 'gembira' / 快乐");

        response.setPronunciation("kuài lè");
        response.setExplanation(
                "快乐 bermaksud gembira atau bahagia. Ia menggambarkan perasaan kegembiraan, kesenangan atau kepuasan yang dirasai apabila seseorang berpuas hati dengan keadaan semasa.");
        response.setExamples(
                "1. 他总是很快乐。\n" +
                        "   Dia sentiasa sangat gembira.\n" +
                        "2. 祝你生日快乐。\n" +
                        "   Selamat hari jadi, semoga gembira.\n" +
                        "3. 我们快乐地度过了假期。\n" +
                        "   Kami telah menghabiskan cuti dengan gembira.");
        response.setAdjective(true);
        return response;
    }

    /**
     * Enhance the response for "sedih" (sad)
     */
    private AiResponse enhanceSedih(AiResponse response) {
        logger.info("Enhancing translation for word 'sedih' / 伤心");

        response.setPronunciation("shāng xīn");
        response.setExplanation(
                "伤心 bermaksud sedih atau dukacita. Ia menggambarkan emosi negatif yang dirasai apabila seseorang mengalami kehilangan, kekecewaan atau situasi yang menyedihkan.");
        response.setExamples(
                "1. 听到这个消息，他感到非常悲伤。\n" +
                        "   Setelah mendengar berita itu, dia berasa sedih.\n" +
                        "2. 电影的结局很悲伤。\n" +
                        "   Pengakhiran filem itu sangat sedih.\n" +
                        "3. 她的眼睛里充满了悲伤。\n" +
                        "   Dia berasa sedih melihat situasi itu.");
        response.setAdjective(true);
        return response;
    }

    /**
     * Enhance the response for "bulat" (round)
     */
    private AiResponse enhanceBulat(AiResponse response) {
        logger.info("Enhancing translation for word 'bulat' / 圆形");

        response.setPronunciation("yuán xíng");
        response.setExplanation(
                "圆形 bermaksud bulat atau berbentuk bulatan. Ia menggambarkan sesuatu objek yang tidak mempunyai sudut tajam, dengan perimeter yang halus dan seragam dari pusat ke tepi.");
        response.setExamples(
                "1. 这个球是圆形的。\n" +
                        "   Bola ini adalah berbentuk bulat.\n" +
                        "2. 月亮是圆形的。\n" +
                        "   Bulan adalah berbentuk bulat.\n" +
                        "3. 这张桌子有一个圆形的表面。\n" +
                        "   Meja ini mempunyai permukaan berbentuk bulat.");
        response.setAdjective(true);
        return response;
    }

    /**
     * Enhance the response for "bujur" (oblong/elliptical)
     */
    private AiResponse enhanceBujur(AiResponse response) {
        logger.info("Enhancing translation for word 'bujur' / 椭圆形");

        response.setPronunciation("Tuǒyuán xíng");
        response.setExplanation(
                "椭圆形 bermaksud bujur atau berbentuk lonjong. Ia adalah bentuk yang menyerupai bulatan tetapi lebih panjang di satu arah, dengan perimeter yang halus dan melengkung secara konsisten.");
        response.setExamples(
                "1. 这个橄榄球是椭圆形的。\n" +
                        "   Bola ragbi ini adalah berbentuk bujur.\n" +
                        "2. 地球的形状接近椭圆形。\n" +
                        "   Bentuk bumi hampir menyerupai bentuk bujur.\n" +
                        "3. 这个盘子是椭圆形的。\n" +
                        "   Piring ini mempunyai bentuk bujur.");
        response.setAdjective(true);
        return response;
    }

    /**
     * Enhance the response for "浪费" (wasteful/wasting)
     */
    private AiResponse enhanceLangfei(AiResponse response) {
        logger.info("Enhancing translation for word '浪费'");

        response.setPronunciation("làngfèi");
        response.setExplanation(
                "浪费 bermaksud membazir atau menggunakan sesuatu dengan tidak bertanggungjawab. Ia menggambarkan tindakan membuang atau menggunakan sumber daya dengan berlebihan dan tidak perlu.");
        response.setExamples(
                "1. 不要浪费食物。\n" +
                        "   Jangan membazir makanan.\n" +
                        "2. 浪费时间是很不好的。\n" +
                        "   Membazir masa adalah sangat tidak baik.\n" +
                        "3. 我们应该避免浪费资源。\n" +
                        "   Kita harus mengelak membazir sumber daya.");
        response.setAdjective(true);
        return response;
    }

    /**
     * Helper method to check if a response needs enhancement
     */
    private boolean isResponseIncomplete(AiResponse response) {
        return response.getExplanation().contains("No explanation available") ||
                response.getPronunciation().contains("No pronunciation available") ||
                response.getExamples().contains("No examples available");
    }
}
