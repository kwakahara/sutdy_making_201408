package jp.ktsystem.kadai201408.k_wakahara;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jp.ktsystem.kadai201408.common.KadaiException;
import jp.ktsystem.kadai201408.common.ErrorCode;

/**
 * ����̏����̃e�L�X�g�t�@�C����ǂݍ��݁A���[���ɉ��������l��Ԃ��N���X
 * 
 * @author wakahara
 */
public class Kadai {

	/**
	 * �ۑ�̎d�l�ʂ�̃N���X�ł��B
	 * 
	 * @param anInputPath
	 * @return
	 * @throws KadaiException
	 */
	public static long calcScoreSum(String anInputPath) throws KadaiException {

		String str = readFile(anInputPath);

		str = str.toUpperCase();

		int[] numlist = changeNumber(str);

		return addition(numlist);

	}

	/**
	 * �t�@�C���̓ǂݍ��݃��\�b�h
	 * 
	 * @param aFileName
	 * @return
	 * @throws KadaiException
	 */
	public static String readFile(String aFileName) throws KadaiException {

		
		try (FileInputStream input = new FileInputStream(aFileName);
				BufferedReader fileLine = openTextFileR(input, "UTF-8");) {

			String line = fileLine.readLine();
			if (line == null) {
				line = "";
			}

			return line;

		} catch (FileNotFoundException e) {
			throw new KadaiException(ErrorCode.FILE_IN_OUT);
		} catch (IOException e) {
			throw new KadaiException(ErrorCode.FILE_IN_OUT);
		} catch (Exception e) {
			throw new KadaiException(ErrorCode.OTHER_ERROR);
		}
	}

	/**
	 * �t�@�C���ǂݍ��݂̃X�g���[������
	 * 
	 * @param aFileInputStr
	 * @param aCharSet
	 * @return
	 * @throws Exception
	 */
	public static BufferedReader openTextFileR(FileInputStream aFileInputStr,
			String charSet) throws Exception {

		return new BufferedReader(new InputStreamReader(skipUTF8BOM(aFileInputStr,
				charSet), charSet));
	}

	/**
	 * UTF-8��BOM���X�L�b�v����
	 */
	public static InputStream skipUTF8BOM(InputStream anInputStream, String aCharSet)
			throws Exception {
		if (!aCharSet.toUpperCase().equals("UTF-8"))
			return anInputStream;
		if (!anInputStream.markSupported()) {
			// �}�[�N�@�\�������ꍇBufferedInputStream��킹��
			anInputStream = new BufferedInputStream(anInputStream);
		}
		anInputStream.mark(3); // �擪�Ƀ}�[�N��t����
		if (anInputStream.available() >= 3) {
			byte b[] = { 0, 0, 0 };
			anInputStream.read(b, 0, 3);
			if (b[0] != (byte) 0xEF || b[1] != (byte) 0xBB
					|| b[2] != (byte) 0xBF) {
				anInputStream.reset();
				// BOM�łȂ��ꍇ�͐擪�܂Ŋ����߂�
			}
		}
		return anInputStream;
	}

	/**
	 * �z��̑����Z���s�����\�b�h
	 * 
	 * @param aNumBox
	 *            : �e�t�@�C���̕�����𐔒l�ɕϊ������z��
	 * @return sum : ���v�l
	 */
	public static long addition(int[] aNumBox) {

		long sum = 0;

		for (int i = 1; i <= aNumBox.length; i++) {
			sum += aNumBox[i - 1] * i;
		}

		return sum;
	}

	/**
	 * �ǂ݂���CSV�t�@�C���𐔒l�ɕϊ����郁�\�b�h
	 * 
	 * @param str
	 *            �@�F�@�t�@�C���̓��e
	 * @return numBox : ���l�ɕϊ������z���Ԃ�
	 * @throws KadaiException
	 */
	public static int[] changeNumber(String str) throws KadaiException {

		// �J���}�ŋ�؂��Ĕz��Ɋi�[
		String[] alphabetArray = str.split(",", -1);
		// �J���}�ŋ�؂�ꂽ�e�u���b�N�̃X�R�A��z��̗v�f�Ɋi�[
		int[] numBox = new int[alphabetArray.length];

		// �����𐔒l�ɒ����ăX�R�A�v�Z
		for (int i = 0; i < alphabetArray.length; i++) {
			for (int j = 0; j < alphabetArray[i].length(); j++) {

				char tempChar = alphabetArray[i].charAt(j);
				if (tempChar <= 'Z' && tempChar >= 'A') {
					numBox[i] += tempChar - 'A' + 1;
				} else {
					throw new KadaiException(ErrorCode.INVALID_STRING);
				}
			}
		}
		return numBox;
	}

}