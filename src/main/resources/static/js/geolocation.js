$("#here").on("click", function() {
    if (!navigator.geolocation) {
        alert("この端末では位置情報が取得できません");
    }else{
        getPosition();
    }
})

// 現在地取得処理
function getPosition() {
    // 現在地を取得
    navigator.geolocation.getCurrentPosition(
        // 取得成功した場合
        function (position) {

            alert("緯度:" + position.coords.latitude + ",経度" + position.coords.longitude);
            document.forms.loc.point.value=position.coords.latitude + "," + position.coords.longitude;
            document.forms.loc.from.value="現在地"
            var f = document.getElementById("loc")
            f.method = "POST";
            f.action = "roulette"
            f.submit();
        },
        // 取得失敗した場合
        function (error) {
            switch (error.code) {
                case 1: //PERMISSION_DENIED
                    alert("位置情報の利用が許可されていません");
                    break;
                case 2: //POSITION_UNAVAILABLE
                    alert("現在位置が取得できませんでした");
                    break;
                case 3: //TIMEOUT
                    alert("タイムアウトになりました");
                    break;
                default:
                    alert("その他のエラー(エラーコード:" + error.code + ")");
                    break;
            }
        }
    );
}