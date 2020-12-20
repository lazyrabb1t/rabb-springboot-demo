package xyz.lazyrabbit.data.redis.util;

public class RedisBitMapUtils {
//    /*********************************************************************************
//     *
//     * 对bitmap的操作
//     *
//     ********************************************************************************/
//
//    /**
//     * 将指定param的值设置为1，{@param param}会经过hash计算进行存储。
//     *
//     * @param key   bitmap结构的key
//     * @param param 要设置偏移的key，该key会经过hash运算。
//     * @param value true：即该位设置为1，否则设置为0
//     * @return 返回设置该value之前的值。
//     */
//    public static Boolean setBit(String key, String param, boolean value) {
//        return stringRedisTemplate.opsForValue().setBit(key, hash(param), value);
//    }
//
//    /**
//     * 将指定param的值设置为0，{@param param}会经过hash计算进行存储。
//     *
//     * @param key   bitmap结构的key
//     * @param param 要移除偏移的key，该key会经过hash运算。
//     * @return 若偏移位上的值为1，那么返回true。
//     */
//    public static Boolean getBit(String key, String param) {
//        return stringRedisTemplate.opsForValue().getBit(key, hash(param));
//    }
//
//
//    /**
//     * 将指定offset偏移量的值设置为1；
//     *
//     * @param key    bitmap结构的key
//     * @param offset 指定的偏移量。
//     * @param value  true：即该位设置为1，否则设置为0
//     * @return 返回设置该value之前的值。
//     */
//    public static Boolean setBit(String key, Long offset, boolean value) {
//        return stringRedisTemplate.opsForValue().setBit(key, offset, value);
//    }
//
//    /**
//     * 将指定offset偏移量的值设置为0；
//     *
//     * @param key    bitmap结构的key
//     * @param offset 指定的偏移量。
//     * @return 若偏移位上的值为1，那么返回true。
//     */
//    public static Boolean getBit(String key, long offset) {
//        return stringRedisTemplate.opsForValue().getBit(key, offset);
//    }
//
//    /**
//     * 统计对应的bitmap上value为1的数量
//     *
//     * @param key bitmap的key
//     * @return value等于1的数量
//     */
//    public static Long bitCount(String key) {
//        return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
//    }
//
//    /**
//     * 统计指定范围中value为1的数量
//     *
//     * @param key   bitMap中的key
//     * @param start 该参数的单位是byte（1byte=8bit），{@code setBit(key,7,true);}进行存储时，单位是bit。那么只需要统计[0,1]便可以统计到上述set的值。
//     * @param end   该参数的单位是byte。
//     * @return 在指定范围[start*8,end*8]内所有value=1的数量
//     */
//    public static Long bitCount(String key, int start, int end) {
//        return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes(), start, end));
//    }
//
//
//    /**
//     * 对一个或多个保存二进制的字符串key进行元操作，并将结果保存到saveKey上。
//     * <p>
//     * bitop and saveKey key [key...]，对一个或多个key逻辑并，结果保存到saveKey。
//     * bitop or saveKey key [key...]，对一个或多个key逻辑或，结果保存到saveKey。
//     * bitop xor saveKey key [key...]，对一个或多个key逻辑异或，结果保存到saveKey。
//     * bitop xor saveKey key，对一个或多个key逻辑非，结果保存到saveKey。
//     * <p>
//     *
//     * @param op      元操作类型；
//     * @param saveKey 元操作后将结果保存到saveKey所在的结构中。
//     * @param desKey  需要进行元操作的类型。
//     * @return 1：返回元操作值。
//     */
//    public static Long bitOp(RedisStringCommands.BitOperation op, String saveKey, String... desKey) {
//        byte[][] bytes = new byte[desKey.length][];
//        for (int i = 0; i < desKey.length; i++) {
//            bytes[i] = desKey[i].getBytes();
//        }
//        return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(op, saveKey.getBytes(), bytes));
//    }
//
//    /**
//     * 对一个或多个保存二进制的字符串key进行元操作，并将结果保存到saveKey上，并返回统计之后的结果。
//     *
//     * @param op      元操作类型；
//     * @param saveKey 元操作后将结果保存到saveKey所在的结构中。
//     * @param desKey  需要进行元操作的类型。
//     * @return 返回saveKey结构上value=1的所有数量值。
//     */
//    public static Long bitOpResult(RedisStringCommands.BitOperation op, String saveKey, String... desKey) {
//        bitOp(op, saveKey, desKey);
//        return bitCount(saveKey);
//    }
//
//
//    /**
//     * guava依赖获取hash值。
//     */
//    private static long hash(String key) {
//        Charset charset = Charset.forName("UTF-8");
//        return Math.abs(Hashing.murmur3_128().hashObject(key, Funnels.stringFunnel(charset)).asInt());
//    }
}
