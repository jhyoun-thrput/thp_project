function getErrMsg(errcode, opt_txt1, opt_txt2, opt_txt3) {
	var errMsg;
	switch (errcode) {
	case "IJCO001":
		errMsg = "登録されました。";
		break;
	case "IJCO002":
		errMsg = "修正されました。";
		break;
	case "IJCO003":
		errMsg = "削除されました。";
		break;
	case "IJCO004":
		errMsg = "保存されました。";
		break;
	case "IJCO005":
		errMsg = "作成されました。";
		break;
	case "IJCO006":
		errMsg = "処理されました。";
		break;
	case "IJCO007":
		errMsg = "出力されました。";
		break;
	case "IJCO008":
		errMsg = "発行されました。";
		break;
	case "IJCO009":
		errMsg = "変更されました。";
		break;
	case "IJCO010":
		errMsg = "申請されました。";
		break;
	case "IJCO011":
		errMsg = "取消されました。";
		break;
	case "IJCO012":
		errMsg = "該当するデータが見つかりません。";
		break;
	case "IJCO013":
		errMsg = opt_txt1 + "件修正されました。";
		break;
	case "IJCO014":
		errMsg = opt_txt1 + "件照会されました。";
		break;
	case "IJCO015":
		errMsg = "変更されたデータがありません。";
		break;
	case "IJCO016":
		errMsg = opt_txt1 + "されたデータがありません。";
		break;
	case "IJCO017":
		errMsg = "既に締切処理が実行されました。";
		break;
	case "IJCO018":
		errMsg = "既に" + opt_txt1 + "は終了しました。";
		break;
	case "IJCO019":
		errMsg = opt_txt1 + "したデータは" + opt_txt2 + "出来ません。";
		break;
	case "IJCO020":
		errMsg = opt_txt1 + "した対象者は" + opt_txt2 + "出来ません。";
		break;
	case "IJCO021":
		errMsg = opt_txt1 + "した" + opt_txt2 + "は" + opt_txt3 + "出来ません。";
		break;
	case "AJCO001":
		errMsg = "登録しますか？";
		break;
	case "AJCO002":
		errMsg = "修正しますか？";
		break;
	case "AJCO003":
		errMsg = "削除しますか？";
		break;
	case "AJCO004":
		errMsg = "保存しますか？";
		break;
	case "AJCO005":
		errMsg = "作成しますか？";
		break;
	case "AJCO006":
		errMsg = "実行しますか？";
		break;
	case "AJCO007":
		errMsg = "出力しますか？";
		break;
	case "AJCO008":
		errMsg = "発行しますか？";
		break;
	case "AJCO009":
		errMsg = "変更しますか？";
		break;
	case "AJCO010":
		errMsg = "申請しますか？";
		break;
	case "AJCO011":
		errMsg = "取消しますか？";
		break;
	case "AJCO012":
		errMsg = opt_txt1 + "しますか？";
		break;
	case "AJCO013":
		errMsg = opt_txt1 + "を確認してください。";
		break;
	case "EJCO001":
		errMsg = "登録に失敗しました。";
		break;
	case "EJCO002":
		errMsg = "修正に失敗しました。";
		break;
	case "EJCO003":
		errMsg = "削除に失敗しました。";
		break;
	case "EJCO004":
		errMsg = "作成に失敗しました。";
		break;
	case "EJCO005":
		errMsg = "処理に失敗しました。";
		break;
	case "EJCO006":
		errMsg = "出力に失敗しました。";
		break;
	case "EJCO007":
		errMsg = "発行に失敗しました。";
		break;
	case "EJCO008":
		errMsg = "変更に失敗しました。";
		break;
	case "EJCO009":
		errMsg = "申請に失敗しました。";
		break;
	case "EJCO010":
		errMsg = "取消に失敗しました。";
		break;
	case "EJCO011":
		errMsg = opt_txt1 + "に失敗しました。";
		break;
	case "EJCO012":
		errMsg = opt_txt1 + "処理に失敗しました。";
		break;
	case "EJCO013":
		errMsg = opt_txt1 + "が入力されていません。";
		break;
	case "EJCO014":
		errMsg = opt_txt1 + "が選択されていません。";
		break;
	case "EJCO015":
		errMsg = opt_txt1 + "を入力してください。";
		break;
	case "EJCO016":
		errMsg = opt_txt1 + "又は" + opt_txt2 + "を入力してください。";
		break;
	case "EJCO017":
		errMsg = opt_txt1 + "は" + opt_txt2 + "より小さくなければなりません。";
		break;
	case "EJCO018":
		errMsg = opt_txt1 + "は" + opt_txt2 + "以上になれません。";
		break;
	case "EJCO019":
		errMsg = opt_txt1 + "は" + opt_txt2 + "未満にはなれません。";
		break;
	case "EJCO020":
		errMsg = opt_txt1 + "は" + opt_txt2 + "以下になれません。";
		break;
	case "EJCO021":
		errMsg = opt_txt1 + "を" + opt_txt2 + "より大きく入力してください。";
		break;
	case "EJCO022":
		errMsg = opt_txt1 + "を" + opt_txt2 + "以上に入力してください。";
		break;
	case "EJCO023":
		errMsg = opt_txt1 + "を" + opt_txt2 + "未満に入力してください。";
		break;
	case "EJCO024":
		errMsg = opt_txt1 + "を" + opt_txt2 + "以下に入力したください。";
		break;
	case "EJCO025":
		errMsg = opt_txt1 + "は" + opt_txt2 + "範囲を超えることは出来ません。";
		break;
	case "EJCO026":
		errMsg = opt_txt1 + "は" + opt_txt2 + "より大きく" + opt_txt3
				+ "より小さい必要があります。";
		break;
	case "EJCO027":
		errMsg = opt_txt1 + "は" + opt_txt2 + "以上" + opt_txt3 + "以下の必要があります。";
		break;
	case "EJCO028":
		errMsg = opt_txt1 + "は" + opt_txt2 + "と同じ必要があります。";
		break;
	case "EJCO029":
		errMsg = opt_txt1 + "は" + opt_txt2 + "より以前は指定できません。";
		break;
	case "EJCO030":
		errMsg = opt_txt1 + "は" + opt_txt2 + "と同じ日又は以前は指定できません。";
		break;
	case "EJCO031":
		errMsg = opt_txt1 + "は" + opt_txt2 + "より以後は指定できません。";
		break;
	case "EJCO032":
		errMsg = opt_txt1 + "は" + opt_txt2 + "と同じ日又は以後は指定できません。";
		break;
	case "EJCO033":
		errMsg = opt_txt1 + "は" + opt_txt2 + "を超えられません。";
		break;
	case "EJCO034":
		errMsg = opt_txt1 + "は" + opt_txt2 + "以後で、かつ" + opt_txt3
				+ "以前の必要があります。";
		break;
	case "EJCO035":
		errMsg = opt_txt1 + "をするデータがありません。";
		break;
	case "EJCO036":
		errMsg = opt_txt1 + "をする対象者がありません。";
		break;
	case "EJCO037":
		errMsg = opt_txt1 + "をする" + opt_txt2 + "がありません。";
		break;
	case "EJCO038":
		errMsg = "既に、" + opt_txt1 + "をした資料です。";
		break;
	case "EJCO039":
		errMsg = "既に、" + opt_txt1 + "をしたデータです。";
		break;
	case "EJCO040":
		errMsg = "既に、" + opt_txt1 + "をした対象者です。";
		break;
	case "EJCO041":
		errMsg = "既に存在しています。";
		break;
	case "EJCO042":
		errMsg = "既に、" + opt_txt1 + "している" + opt_txt2 + "です。";
		break;
	case "EJCO043":
		errMsg = "既に、" + opt_txt1 + "した" + opt_txt2 + "です。";
		break;
	case "EJCO044":
		errMsg = "先に" + opt_txt1 + "をしてください。";
		break;
	case "EJCO045":
		errMsg = opt_txt1 + "はありません。";
		break;
	case "EJCO046":
		errMsg = "該当するコードがありません。";
		break;
	case "EJCO047":
		errMsg = "入力されたコードに該当するコードは存在しません。";
		break;
	case "EJCO048":
		errMsg = "入力した" + opt_txt1 + "に該当する" + opt_txt2 + "は存在しません。";
		break;
	case "EJCO049":
		errMsg = opt_txt1 + "である" + opt_txt2 + "に該当する" + opt_txt3 + "は存在しません。";
		break;
	case "EJCO050":
		errMsg = opt_txt1 + "の入力に誤りがあります。";
		break;
	case "EJCO051":
		errMsg = opt_txt1 + "エラーです。";
		break;
	case "EJCO052":
		errMsg = opt_txt1 + "は" + opt_txt2 + "桁で入力してください。";
		break;
	case "EJCO053":
		errMsg = opt_txt1 + "は正しい個人番号ではありません。";
		break;
	case "EJCO054":
		errMsg = opt_txt1 + "は正しい" + opt_txt2 + "ではありません。";
		break;
	case "EJCO055":
		errMsg = "検索条件は少なくとも一つ入力してください。";
		break;
	case "EJCO056":
		errMsg = opt_txt1 + "を少なくても" + opt_txt2 + "件入力してください。";
		break;

	// 住記(RE) //////////////////////////////////////////////////////
	case "AJRE001":
		errMsg = "同一市町村で転出は不可能です。";
		break;
	case "AJRE002":
		errMsg = "世帯情報を先に照会してください。";
		break;
	case "AJRE003":
		errMsg = "構成員が一人だけです。処理メニューは（全部）を選択してください。";
		break;
	case "AJRE004":
		errMsg = "すべての構成員を処理する場合、処理メニューは（全部）を選択してください。";
		break;
	case "AJRE005":
		errMsg = "続柄関係１は、必ず入力してください。";
		break;
	case "AJRE006":
		errMsg = "世帯主を入力して下さい。";
		break;
	case "AJRE007":
		errMsg = "世帯主は一名のみ入力できます。";
		break;
	case "AJRE008":
		errMsg = "処理する構成員を選択してください。";
		break;
	case "AJRE009":
		errMsg = "世帯主は、続柄関係１で入力してください。";
		break;
	case "AJRE010":
		errMsg = "上位続柄関係を先に入力してください。";
		break;
	case "AJRE011":
		errMsg = "世帯主は、続柄関係１で入力してください。";
		break;
	case "AJRE012":
		errMsg = "世帯主は、下位続柄関係を入力することは出来ません。";
		break;
	case "AJRE013":
		errMsg = "国名を選択してください.";
		break;
	case "AJRE014":
		errMsg = "主変保留状態で保存しますか";
		break;
	case "AJRE015":
		errMsg = "新しい世帯主が１５歳未満です。この状態で保存しますか？";
		break;
	case "AJRE016":
		errMsg = "該当世帯に転出予定者が存在しません。";
		break;
	case "AJRE017":
		errMsg = "異なる転出先情報を持った構成員が存在します。";
		break;
	case "AJRE018":
		errMsg = "郵便番号が確認できません。全国住所から入力してください。";
		break;
	case "AJRE019":
		errMsg = "選択された構成員はいません。";
		break;
	case "AJRE020":
		errMsg = "構成員の性別と続柄に矛盾があります。 ";
		break;
	case "AJRE021":
		errMsg = "世帯主がいません。";
		break;
	case "AJRE022":
		errMsg = "世帯主が２人以上います。";
		break;
	case "AJRE023":
		errMsg = "世帯主が15歳未満です。この状態で保存しますか？";
		break;
	case "AJRE024":
		errMsg = "続柄エラーです";
		break;
	case "AJRE025":
		errMsg = "";
		break;
	case "AJRE026":
		errMsg = "既存世帯員は世帯主となることができません。";
		break;
	case "AJRE027":
		errMsg = "世帯主を変更せずに世帯構成員の続柄を変更することはできません。";
		break;
	case "AJRE028":
		errMsg = "住登者ではない場合、続柄を変更することができません。";
		break;
	case "AJRE029":
		errMsg = "検索した世帯が見つかりません。";
		break;
	case "AJRE030":
		errMsg = "選択した構成員が見つかりません。";
		break;
	case "AJRE031":
		errMsg = "生年月日と異動日が違います。";
		break;
	case "AJRE032":
		errMsg = "元世帯と先世帯が同じ世帯です。";
		break;
	case "AJRE033":
		errMsg = "構成員が異動した状態では検索できません。";
		break;
	case "AJRE034":
		errMsg = "元世帯の構成員が1名です。";
		break;
	case "AJRE035":
		errMsg = "元世帯に選択した構成員がいません。";
		break;
	case "AJRE036":
		errMsg = "元世帯の全構成員を異動することはできません。";
		break;
	case "AJRE037":
		errMsg = "先世帯に選択した構成員がいません。";
		break;
	case "AJRE038":
		errMsg = "先世帯で異動可能な構成員がいません。";
		break;
	case "AJRE039":
		errMsg = "追加した構成員がいません。";
		break;
	case "AJRE040":
		errMsg = "元世帯の住所と先世帯の住所が一致しません。";
		break;
	case "AJRE041":
		errMsg = "世帯主が変更されませんでした。";
		break;
	case "AJRE042":
		errMsg = "選択された情報は使用中です。";
		break;
	case "AJRE043":
		errMsg = "同じ住所で転居することはできません。";
		break;
	case "AJRE044":
		errMsg = "住民票コードがない構成員がいます。";
		break;
	case "AJRE045":
		errMsg = "本籍と筆頭者は一方のみ「なし」とすることはできません。";
		break;
	case "AJRE046":
		errMsg = "住民票コードは11桁で入力してください。";
		break;
	case "AJRE047":
		errMsg = "異動日が未来日です。";
		break;
	case "AJRE048":
		errMsg = "構成員の性別と続柄に矛盾があります。 ";
		break;
	case "AJRE049":
		errMsg = "異動日が世帯の最終異動日より以前です。続けますか？";
		break;
	case "AJRE050":
		errMsg = "世帯主が２人以上います。";
		break;
	case "AJRE051":
		errMsg = "世帯主が15歳未満です。この状態で保存しますか？";
		break;
	case "AJRE052":
		errMsg = "前住所に不備があります。";
		break;
	case "AJRE053":
		errMsg = "前世帯主を入力してください。";
		break;
	case "AJRE054":
		errMsg = "短期間の再転入であろため。";
		break;
	case "AJRE055":
		errMsg = "届出日が未来日です。";
		break;
	case "AJRE056":
		errMsg = "異動日が届出日よりも未来です。";
		break;
	case "AJRE057":
		errMsg = "異動日が世帯の最終異動日より以前です。続けますか？";
		break;

	// 国保・年金(WE) //////////////////////////////////////////////////////
	case "IJWE001":
		errMsg = opt_txt1 + "を" + opt_txt2 + "しました。";
		break;
	case "IJWE002":
		errMsg = "対象者は" + opt_txt1 + "です。";
		break;
	case "IJWE003":
		errMsg = opt_txt1 + "の" + opt_txt2 + "は" + opt_txt3 + "です。";
		break;
	case "IJWE004":
		errMsg = "今修正された" + opt_txt1 + "は最新" + opt_txt2 + "ではありません。"
		break;
	case "IJWE005":
		errMsg = "更新されました。"
		break;
	case "IJWE006":
		errMsg = opt_txt1 + "ではありません。"
		break;
	case "IJWE007":
		errMsg = opt_txt1 + "から" + opt_txt2 + "以内です。";
		break;
	case "IJWE008":
		errMsg = "現在のページの最後のデータです。";
		break;
	case "IJWE009":
		errMsg = "処理されました。ただし該当する所得照会発行番号は存在しません。";
		break;
	case "IJWE010":
		errMsg = opt_txt1 + "の場合のみ" + opt_txt2 + "ができます。";
		break;
	case "IJWE011":
		errMsg = "検索結果情報が更新されたので再検索してください。";
		break;
	case "IJWE012":
		errMsg = "仮受付は新規、仮受付中の場合以外にはできません。";
		break;
	case "IJWE013":
		errMsg = "検索後に" + opt_txt1 + "ができます。";
		break;
	case "IJWE014":
		errMsg = "判定結果が減免非該当の場合は、減免不許可のみできます。";
		break;
	case "IJWE015":
		errMsg = "本申請、減免確定、不許可、減免取消の場合は計算出来ません。";
		break;
	case "IJWE016":
		errMsg = opt_txt1 + "の場合は、" + opt_txt2 + "のみできます。";
		break;
	case "IJWE017":
		errMsg = opt_txt1 + "の場合は" + opt_txt2 + "ができません。";
		break;
	case "IJWE018":
		errMsg = "該当する減免率がありません。";
		break;

	case "AJWE001":
		errMsg = opt_txt1 + "が" + opt_txt2 + "からです。";
		break;
	case "AJWE002":
		errMsg = opt_txt1 + "が" + opt_txt2 + "までです。";
		break;
	case "AJWE003":
		errMsg = opt_txt1 + "が" + opt_txt2 + "から" + opt_txt3 + "までです";
		break;
	case "AJWE004":
		errMsg = "引き続き" + opt_txt1 + "処理を行いますか。";
		break;
	case "AJWE005":
		errMsg = opt_txt1 + "して" + opt_txt2 + "を適用しますか。";
		break;
	case "AJWE006":
		errMsg = "対象者の" + opt_txt1 + "を" + opt_txt2 + "しても宜しいですか。";
		break;
	case "AJWE007":
		errMsg = opt_txt1 + "が" + opt_txt2 + "と" + opt_txt3 + "の合計と異なります。";
		break;
	case "AJWE008":
		errMsg = "続けますか。";
		break;
	case "AJWE009":
		errMsg = opt_txt1 + "が" + opt_txt2 + "ではありません。";
		break;
	case "AJWE010":
		errMsg = opt_txt1 + "が" + opt_txt2 + "です。";
		break;
	case "AJWE011":
		errMsg = opt_txt1 + "と" + opt_txt2 + "は同様になりません。";
		break;
	case "AJWE012":
		errMsg = opt_txt1 + "が" + opt_txt2 + "を" + opt_txt3 + "しました。";
		break;
	case "AJWE013":
		errMsg = opt_txt1 + "が" + opt_txt2 + "の未満です。";
		break;
	case "AJWE014":
		errMsg = "よろしいですか。";
		break;
	case "AJWE015":
		errMsg = opt_txt1 + "と" + opt_txt2 + "が異なります。";
		break;
	case "AJWE016":
		errMsg = "先に" + opt_txt1 + "を" + opt_txt2 + "してください。";
		break;
	case "AJWE017":
		errMsg = "更新しますか？";
		break;
	case "AJWE018":
		errMsg = opt_txt1 + "が" + opt_txt2 + "以上です。";
		break;
	case "AJWE019":
		errMsg = opt_txt1 + "された" + opt_txt2 + "を" + opt_txt3 + "しますか。";
		break;
	case "AJWE020":
		errMsg = "保存後、次のデータに移動しますか。";
		break;
	case "AJWE021":
		errMsg = opt_txt1 + "が" + opt_txt2 + "されます。";
		break;
	case "AJWE022":
		errMsg = "この通知番号の請求内訳及び関連給付内訳がすべて削除されます。";
		break;
	case "AJWE023":
		errMsg = "更新に失敗しました。";
		break;
	case "AJWE024":
		errMsg = opt_txt1 + "より" + opt_txt2 + "が大きいです。";
		break;
	case "AJWE025":
		errMsg = opt_txt1 + "の場合は" + opt_txt2 + "を入力してください。";
		break;
	case "AJWE026":
		errMsg = "最小一行は入力してください。";
		break;
	case "AJWE027":
		errMsg = opt_txt1 + "の" + opt_txt2 + "行目の情報を正しく入力してください。";
		break;
	case "AJWE028":
		errMsg = "削除する行を選択してください。";
		break;
	case "AJWE029":
		errMsg = "沖縄における最終の住所がありません。よろしいですか？";
		break;
	case "AJWE030":
		errMsg = "履歴なしで作成します。";
		break;
	case "AJWE031":
		errMsg = opt_txt1 + "、" + opt_txt2 + "です。" + opt_txt3 + "ください。";
		break;

	case "EJWE001":
		errMsg = opt_txt1 + "は" + opt_txt2 + "の" + opt_txt3 + "の必要があります。";
		break;
	case "EJWE002":
		errMsg = "もう、" + opt_txt1 + "の" + opt_txt2 + "はできません。";
		break;
	case "EJWE003":
		errMsg = opt_txt1 + "は" + opt_txt2 + "と同じ日は指定できません。";
		break;
	case "EJWE004":
		errMsg = opt_txt1 + "と" + opt_txt2 + "は同じ日に指定しなければなりません。";
		break;
	case "EJWE005":
		errMsg = opt_txt1 + "処理を行って下さい。";
		break;
	case "EJWE006":
		errMsg = opt_txt1 + "処理は" + opt_txt2 + "にて行って下さい。";
		break;
	case "EJWE007":
		errMsg = opt_txt1 + "より" + opt_txt2 + "が" + opt_txt3 + "です。";
		break;
	case "EJWE008":
		errMsg = "もう一度" + opt_txt1 + "を確認した上で、" + opt_txt2 + "処理を行って下さい。";
		break;
	case "EJWE009":
		errMsg = opt_txt1 + "の" + opt_txt2 + "が不適切です。";
		break;
	case "EJWE010":
		errMsg = opt_txt1 + "は" + opt_txt2 + "のみ有効です。";
		break;
	case "EJWE011":
		errMsg = "対象者は" + opt_txt1 + "ではありません。";
		break;
	case "EJWE012":
		errMsg = opt_txt1 + "を選択してください。";
		break;
	case "EJWE013":
		errMsg = opt_txt1 + "は" + opt_txt2 + "ができません。"
		break;
	case "EJWE014":
		errMsg = "当初データの処理区分は変更できません。";
		break;
	case "EJWE015":
		errMsg = "変更をする場合は履歴作成をチェックしてください。";
		break;
	case "EJWE016":
		errMsg = "初データではないので、処理区分を当初に変更できません。";
		break;
	case "EJWE017":
		errMsg = "履歴作成の時、処理区分を当初に選択できません。";
		break;
	case "EJWE018":
		errMsg = "処理区分を変更してください。";
		break;
	case "EJWE019":
		errMsg = opt_txt1 + "と" + opt_txt2 + "にいずれか" + opt_txt3 + "済みの場合は全て"
				+ opt_txt3 + "すべきです。";
		break;
	case "EJWE020":
		errMsg = "実行環境の一時的な問題によって実行が失敗しました。もう一度、処理を行って下さい。";
		break;		

	// /////////////////////////////////////////////////////////////////////

	// 個人市民税(IX) //////////////////////////////////////////////////////
	case "AJIX001":
		errMsg = opt_txt1 + "が存在します。" + opt_txt2 + "を行いますか？";
		break;
	case "EJIX001":
		errMsg = opt_txt1 + "された" + opt_txt2 + "に該当する" + opt_txt3 + "が存在しません。";
		break;
	case "EJIX002":
		errMsg = opt_txt1 + "です。";
		break;
	case "EJIX003":
		errMsg = opt_txt1 + "は" + opt_txt2 + "選択してください。";
		break;
	case "EJIX004":
		errMsg = opt_txt1 + "以前" + opt_txt2 + "は" + opt_txt3 + "できません。";
		break;
	case "EJIX005":
		errMsg = opt_txt1 + "以後" + opt_txt2 + "は" + opt_txt3 + "できません。";
		break;

	// /////////////////////////////////////////////////////////////////////

	// 軽自動車税(LX) //////////////////////////////////////////////////////
	case "IJLX001":
		errMsg = "複写されました。";
		break;

	case "AJLX001":
		errMsg = opt_txt1 + "を行います。よろしいですか。";
		break;

	case "EJLX001":
		errMsg = "入力された" + opt_txt1 + "が既に存在します。";
		break;

	case "EJLX002":
		errMsg = "既に" + opt_txt1 + "されているため、" + opt_txt2 + "はできません。";
		break;

	case "EJLX003":
		errMsg = "選択されている年度での" + opt_txt1 + "はできません。";
		break;

	case "EJLX004":
		errMsg = opt_txt1 + "、" + opt_txt2 + "のどちらかは入力してください。";
		break;

	case "EJLX005":
		errMsg = "選択された" + opt_txt1 + "は" + opt_txt2 + "で行ってください。";
		break;

	case "EJLX006":
		errMsg = opt_txt1 + "から選択してください。";
		break;

	case "EJLX007":
		errMsg = opt_txt1 + "が" + opt_txt2 + "の場合は" + opt_txt3 + "できません。"
		break;

	case "EJLX008":
		errMsg = "入力された期間と重なるデータが既に存在しています。";
		break;

	case "EJLX009":
		errMsg = opt_txt1 + "が正しくありません。"
		break;

	case "EJLX010":
		errMsg = "入力された" + opt_txt1 + "は存在しません。";
		break;

	case "EJLX011":
		errMsg = "選択された" + opt_txt1 + "では入力した" + opt_txt2 + "は" + opt_txt3
				+ "できません。";
		break;

	case "EJLX012":
		errMsg = opt_txt1 + "されていないため、" + opt_txt2 + "できません。";
		break;

	case "EJLX013":
		errMsg = opt_txt1 + "に" + opt_txt2 + "は入力できません。";
		break;

	case "EJLX014":
		errMsg = opt_txt1 + "年度に同じ" + opt_txt2 + "が既に存在しています。";
		break;

	// /////////////////////////////////////////////////////////////////////

	// 統合収納(RC) ////////////////////////////////////////////////////////
	case "IJRC001":
		errMsg = opt_txt1 + "確定されました。";
		break;

	case "AJRC001":
		errMsg = opt_txt1 + "が" + opt_txt2 + "ですが、" + opt_txt3 + "しても宜しいですか？";
		break;

	case "EJRC001":
		errMsg = opt_txt1 + "する" + opt_txt2 + "があるません。";
		break;

	case "EJRC002":
		errMsg = opt_txt1 + "は" + opt_txt2 + "です。確認した上で再度入力して下さい。";
		break;

	case "EJRC003":
		errMsg = opt_txt1 + "の中" + opt_txt2 + "が発生しました。";
		break;

	case "EJRC004":
		errMsg = opt_txt1 + "の中少なくとも一つ以上入力してください。";
		break;

	case "EJRC005":
		errMsg = opt_txt1 + "を" + opt_txt2 + "しました。";
		break;

	case "EJRC006":
		errMsg = opt_txt1 + "は検索条件として必須です。入力を確認して下さい。";
		break;

	case "EJRC007":
		errMsg = opt_txt1 + "は出来ません。" + opt_txt2 + "して下さい。";
		break;

	// /////////////////////////////////////////////////////////////////////

	// 法人市民税(CX) ////////////////////////////////////////////////////////
	case "CXCM001":
		errMsg = "既に登録されている支店です。";
		break;

	case "CXCM002":
		errMsg = opt_txt1 + "に該当する" + opt_txt2 + "が見つかりませんでした。";
		break;

	case "CXCM003":
		errMsg = "削除する支店を選択して下さい。";
		break;

	case "CXCM004":
		errMsg = "削除する支店が存在しておりません。";
		break;

	case "CXCM005":
		errMsg = "支店情報がありません。";
		break;

	case "CXCM006":
		errMsg = "該当法人は" + "\n\n" + "法人名：「" + opt_txt1 + "」" + "\n\n"
				+ "法人番号：「" + opt_txt2 + "」の支店で登録されています。";
		break;

	case "CXCM007":
		errMsg = "該当法人はすでに支店・営業所マスターに登録されています。";
		break;

	case "CXCM008":
		errMsg = "最初の履歴です。";
		break;

	case "CXCM009":
		errMsg = "最後の履歴です。";
		break;

	case "CXCM010":
		errMsg = "先に" + opt_txt1 + "を行って下さい。";
		break;

	case "CXCM011":
		errMsg = opt_txt1 + "された" + opt_txt2 + "に該当する" + opt_txt3
				+ "が見つかりませんでした。";
		break;

	case "CXCM012":
		errMsg = "該当法人は、既に、" + opt_txt1 + "されている法人です。";
		break;

	case "CXCM013":
		errMsg = "該当法人が存在しません。";
		break;

	case "CXCM014":
		errMsg = "分割区分が「非分割」の法人の支店営業所の登録は出来ません。";
		break;

	case "CXCM015":
		errMsg = "該当法人は非課税対象となっております。" + "\n\n" + "均等割区分の設定は出来ません。";
		break;

	case "CXCM016":
		errMsg = "均等割区分に誤りがあります。" + "\n\n" + "正しい均等割額は" + opt_txt1 + "円です。";
		break;

	case "CXCM017":
		errMsg = "分割区分が非分割ですので、「総従業員」、「適用従業員」、「設置従業員」が一致する必要があります。";
		break;

	case "CXCM018":
		errMsg = opt_txt1 + "日付の形式に誤りがあります。";
		break;

	case "CXCM019":
		errMsg = opt_txt1 + "を再び入力して下さい。";
		break;

	case "CXCM020":
		errMsg = "除却では「解散日」,「閉鎖日」,「除却日」,「廃止日」の中で必ず一つだけ入力できます。";
		break;

	case "CXCM021":
		errMsg = "画面のクリアを行います。宜しいでしょうか。";
		break;

	case "CXRM001":
		errMsg = "申告対象抽出に " + opt_txt1 + "しました。";
		break;

	case "CXRM002":
		errMsg = "既に抽出された申告対象です。";
		break;

	case "CXRM003":
		errMsg = "照会画面での申告書更新は出来ません。";
		break;

	case "CXRM004":
		errMsg = "同じ事業年度の内に存在します。";
		break;

	case "CXRM005":
		errMsg = "予定・中間申告は同じ事業開始日持つ事は出来ません。";
		break;

	case "CXRM006":
		errMsg = "確定申告後、予定・中間申告は同じ事業開始日持つ事は出来ません。";
		break;

	case "CXRM007":
		errMsg = "事業期間が法人マスタの情報と一致しておりません。";
		break;

	case "CXRM008":
		errMsg = "再び更正理由を選択してください" + "\n\n" + "参考コード:200090, 204020, 300090";
		break;

	case "CXRM009":
		errMsg = "還付充当処理中の申告の変更は出来ません。";
		break;

	case "CXRM010":
		errMsg = opt_txt1 + "の事業年度は変更出来ません。";
		break;

	case "CXRM011":
		errMsg = "申告書の変更は事業年度別に最新申告のみ可能です。";
		break;

	}
	return errMsg;
}
