function payWithKakaoPay() {
  const IMP = window.IMP;
  IMP.init("imp00456485"); // 가맹점 식별코드를 입력해주세요.

  const sellNum = document.getElementById("sellNum").value;
  const pricePerPerson = parseInt(document.querySelector('.tour_booking_amount_area ul li').textContent);
  const amount = sellNum * pricePerPerson;

  IMP.request_pay(
    {
      pg: "kakaopay",
      pay_method: "kakaopay",
      merchant_uid: "merchant_" + new Date().getTime(),
      m_redirect_url: "https://example.com/payments/complete",
    },
    function (rsp) {
      if (rsp.success) {
        // 결제 성공 시 처리
        var impUid = rsp.imp_uid;
        var merchantUid = rsp.merchant_uid;
        console.log(impUid);
        console.log(merchantUid);

        // Ajax를 사용하여 서버에 결제 정보 전달
        $.ajax({
          type: "POST",
          url: "/planner/main/insertMember/" + planId,
          data: {
            impUid: impUid,
            merchantUid: merchantUid,
          },
          success: function (response) {
            // 서버로부터 응답을 받았을 때 실행할 코드 작성
            console.log(response);
            location.href = "/planner/main/" + planId;
          },
          error: function (xhr, status, error) {
            // 에러 발생시 실행할 코드 작성
            console.log(xhr);
            console.log(status);
            console.log(error);
          },
        });

        alert("결제가 완료되었습니다.");
      } else {
        // 결제 실패 시 처리
        alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
      }
    }
  );
}





