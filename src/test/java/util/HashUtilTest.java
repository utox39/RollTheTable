package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HashUtilTest {
    @Test
    void digestReturnsKnownSha256ForAsciiInput() {
        byte[] digest = HashUtil.digest("abc");

        assertEquals(
                "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad",
                HashUtil.hashToHexString(digest)
        );
    }

    @Test
    void digestReturnsKnownSha256ForEmptyInput() {
        byte[] digest = HashUtil.digest("");

        assertEquals(
                "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
                HashUtil.hashToHexString(digest)
        );
    }

    @Test
    void hashToHexStringPreservesLeadingZeroesAndUnsignedBytes() {
        byte[] bytes = {0x00, 0x0f, 0x10, (byte) 0xff};

        assertEquals("000f10ff", HashUtil.hashToHexString(bytes));
    }

    @Test
    void digestUsesUtf8Encoding() {
        assertEquals(
                "850f7dc43910ff890f8879c0ed26fe697c93a067ad93a7d50f466a7028a9bf4e",
                HashUtil.hashToHexString(HashUtil.digest("café"))
        );
    }
}
