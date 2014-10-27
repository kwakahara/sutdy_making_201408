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
 * 特定の書式のテキストファイルを読み込み、ルールに沿った数値を返すクラス
 * 
 * @author wakahara
 */
public class Kadai {

	/**
	 * 課題の仕様通りのクラスです。
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
	 * ファイルの読み込みメソッド
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
	 * ファイル読み込みのストリーム生成
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
	 * UTF-8のBOMをスキップする
	 */
	public static InputStream skipUTF8BOM(InputStream anInputStream, String aCharSet)
			throws Exception {
		if (!aCharSet.toUpperCase().equals("UTF-8"))
			return anInputStream;
		if (!anInputStream.markSupported()) {
			// マーク機能が無い場合BufferedInputStreamを被せる
			anInputStream = new BufferedInputStream(anInputStream);
		}
		anInputStream.mark(3); // 先頭にマークを付ける
		if (anInputStream.available() >= 3) {
			byte b[] = { 0, 0, 0 };
			anInputStream.read(b, 0, 3);
			if (b[0] != (byte) 0xEF || b[1] != (byte) 0xBB
					|| b[2] != (byte) 0xBF) {
				anInputStream.reset();
				// BOMでない場合は先頭まで巻き戻す
			}
		}
		return anInputStream;
	}

	/**
	 * 配列の足し算を行うメソッド
	 * 
	 * @param aNumBox
	 *            : 各ファイルの文字列を数値に変換した配列
	 * @return sum : 合計値
	 */
	public static long addition(int[] aNumBox) {

		long sum = 0;

		for (int i = 1; i <= aNumBox.length; i++) {
			sum += aNumBox[i - 1] * i;
		}

		return sum;
	}

	/**
	 * 読みこんだCSVファイルを数値に変換するメソッド
	 * 
	 * @param str
	 *            　：　ファイルの内容
	 * @return numBox : 数値に変換した配列を返す
	 * @throws KadaiException
	 */
	public static int[] changeNumber(String str) throws KadaiException {

		// カンマで区切って配列に格納
		String[] alphabetArray = str.split(",", -1);
		// カンマで区切られた各ブロックのスコアを配列の要素に格納
		int[] numBox = new int[alphabetArray.length];

		// 文字を数値に直してスコア計算
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