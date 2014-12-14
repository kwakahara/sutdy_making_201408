package jp.ktsystem.kadai201408.test;

import jp.ktsystem.kadai201408.common.ErrorCode;
import jp.ktsystem.kadai201408.common.KadaiException;
import jp.ktsystem.kadai201408.k_wakahara.Kadai;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * �ۑ�e�X�g�N���X(Lv1)
 * @author k_wakahara
 * @since 2014/07/02
 */
@RunWith(JUnit4.class)
public class KadaiTest201408_Lv1 {

	// �t�@�C���p�X(���ɍ��킹�Ē�`���Ă�������)
	private static final String FILE_DIRECTORY = "KadaiTest\\";

	
	@Test
	public void testCase001() {
		assertEquals("testCase001.txt", 0);
	}

	@Test
	public void testCase002() {
		assertEquals("testCase002.txt", 7);
	}
	@Test
	public void testCase003() {
		assertEquals("testCase003.txt", 26);
	}

	@Test
	public void testCase004() {
		assertEquals("testCase004.txt", 30);
	}

	@Test
	public void testCase005() {
		assertEquals("testCase005.txt", 6);
	}

	@Test
	public void testCase006() {
		assertEquals("testCase006.txt", 123);
	}

	@Test
	public void testCase007() {
		assertEquals("testCase007.txt", 123);
	}

	@Test
	public void testCase008() {
		assertEquals("testCase008.txt", 123);
	}

	@Test
	public void testCase009() {
		assertEquals("testCase009.txt", 168);
	}

	@Test
	public void testCase010() {
		assertEquals("testCase010.txt", 123);
	}

	@Test
	public void testCase011() {
		assertEquals("testCase011.txt", 123);
	}

	@Test
	public void testCase101() {
		assertFail(null, ErrorCode.FILE_IN_OUT);
	}

	@Test
	public void testCase102() {
		assertFail("testCase102.txt", ErrorCode.FILE_IN_OUT);
	}

	@Test
	public void testCase103() {
		assertFail("testCase103.txt", ErrorCode.FILE_IN_OUT);
	}

	@Test
	public void testCase104() {
		assertFail("testCase104.txt", ErrorCode.INVALID_STRING);
	}

	@Test
	public void testCase105() {
		assertFail("testCase105.txt", ErrorCode.INVALID_STRING);
	}

	@Test
	public void testCase106() {
		assertFail("testCase106.txt", ErrorCode.INVALID_STRING);
	}

	/**
	 * ����n�e�X�g
	 * @param anInputPath ���̓t�@�C���p�X
	 * @param aScore ����I�����̃X�R�A
	 */
	private void assertEquals(String anInputPath, long aScore) {

		try {
			Assert.assertEquals(aScore, Kadai.calcScoreSum(FILE_DIRECTORY + anInputPath));
		} catch (KadaiException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * �ُ�n�e�X�g
	 * @param anInputPath ���̓t�@�C���p�X
	 * @param anErrorCode �ُ�I�����̃G���[�R�[�h
	 */
	private void assertFail(String anInputPath, ErrorCode anErrorCode) {

		try {
			Kadai.calcScoreSum(FILE_DIRECTORY + anInputPath);
			Assert.fail();
		} catch (KadaiException e) {
			Assert.assertEquals(anErrorCode.getErrorCode(), e.getErrorCode());
		}
	}

}
