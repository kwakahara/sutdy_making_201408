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
 * パッケージ：jp.ktsystem.kadai201408.k_wakahara
 * 継承クラス：無し
 * インターフェース：無し
 * 直系の既知サブクラス：無し
 * 課題の仕様を満たすクラス
 * @author k_wakahara
 */
public class Kadai {

	/**
	 * 特定の書式のテキストファイルを読み込み、ルールに沿った数値を返すメソッド
	 * 
	 * @param anInputPath：ファイルパス
	 * @return　合計値
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
	 * @param fileName：ファイルの名前
	 * @return 読み込んだファイルの1行目
	 * @throws KadaiException
	 */
	public static String readFile(String fileName) throws KadaiException {

		String line = "";

		try {
			FileInputStream input = new FileInputStream(fileName);
			BufferedReader fileLine = openTextFileR(input, "UTF-8");

			line = fileLine.readLine();
			if (line == null) {
				line = "";
			}

			fileLine.close();

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
	 * ファイル読み込みのストリーム生成するメソッド
	 * 
	 * @param fis：ファイルストリーム
	 * @param charSet：エンコード
	 * @return　BufferedReader
	 * @throws Exception
	 */
	public static BufferedReader openTextFileR(FileInputStream fis,
			String charSet) throws Exception {

		return new BufferedReader(new InputStreamReader(skipUTF8BOM(fis,
				charSet), charSet));
	}

	/**
	 * UTF-8のBOMをスキップするメソッド
	 * @param is: 読み取りファイルストリーム
	 * @param charSet:エンコード
	 * @return BOMが存在すればBOMをスキップしたストリーム
	 * なければ何も処理をしていないストリーム
	 * 
	 */
	public static InputStream skipUTF8BOM(InputStream is, String charSet)
			throws Exception {
		if (!charSet.toUpperCase().equals("UTF-8"))
			return is;
		if (!is.markSupported()) {
			// マーク機能が無い場合BufferedInputStreamを被せる
			is = new BufferedInputStream(is);
		}
		is.mark(3); // 先頭にマークを付ける
		if (is.available() >= 3) {
			byte b[] = { 0, 0, 0 };
			is.read(b, 0, 3);
			if (b[0] != (byte) 0xEF || b[1] != (byte) 0xBB
					|| b[2] != (byte) 0xBF) {
				is.reset();
				// BOMでない場合は先頭まで巻き戻す
			}
		}
		return is;
	}

	/**
	 * 配列の足し算を行うメソッド
	 * 
	 * @param numBox　： 和を求める配列
	 * @return sum : 合計値
	 */
	public static long addition(int[] numBox) {

		long sum = 0;

		for (int i = 1; i <= numBox.length; i++) {
			sum += numBox[i - 1] * i;
		}

		return sum;
	}

	/**
	 * 読みこんだCSVファイルを数値に変換するメソッド
	 * 
	 * @param str : 数値に変換する文字列
	 * @return 数値に変換した配列を返す
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