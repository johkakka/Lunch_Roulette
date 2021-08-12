$("#here").on("click", function() {
    if (!navigator.geolocation) {
        alert("この端末では位置情報が取得できません");
    }else{
        alert("現在この機能は利用できません")
    }
    //getPosition()
})

// 現在地取得処理
function getPosition() {
    var element = document.getElementById("nudge");

    var showNudgeBanner = function() {
        nudge.style.display = "block";
    };

    var hideNudgeBanner = function() {
        nudge.style.display = "none";
    };

    var nudgeTimeoutId = setTimeout(showNudgeBanner, 5000);

    // 現在地を取得
    navigator.geolocation.getCurrentPosition(
        // 取得成功した場合
        function (position) {
            hideNudgeBanner();
            // We have the location, don't display banner
            clearTimeout(nudgeTimeoutId);

            alert("緯度:" + position.coords.latitude + ",経度" + position.coords.longitude);
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
                    showNudgeBanner();
                    alert("タイムアウトになりました");
                    break;
                default:
                    alert("その他のエラー(エラーコード:" + error.code + ")");
                    break;
            }
        }
    );
}