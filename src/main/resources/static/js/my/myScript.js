window.onload = function (){
//// my/info ////

    // 연락처 수정 //
    const btnChangeHp = document.getElementById('btnChangeHp');
    const hp1 = document.getElementsByName('hp1')[0];
    const hp2 = document.getElementsByName('hp2')[0];
    const hp3 = document.getElementsByName('hp3')[0];

    btnChangeHp.onclick = function (event) {
        // input태그 활성화
        if (btnChangeHp.className === 'change') {
            hp1.value = "";
            hp2.value = "";
            hp3.value = "";
            hp1.style.border = "1px solid #999";
            hp2.style.border = "1px solid #999";
            hp3.style.border = "1px solid #999";
            hp1.readOnly = false;
            hp2.readOnly = false;
            hp3.readOnly = false;
            btnChangeHp.classList.remove('change');
            btnChangeHp.classList.add('save');

        } else if (btnChangeHp.className === 'save') {
            // 수정 연락처 저장
            let saveHp1 = hp1.value;
            let saveHp2 = hp2.value;
            let saveHp3 = hp3.value;
            let userHp = saveHp1 + "-" + saveHp2 + "-" + saveHp3;
            const userId = document.getElementById('userId').innerText;
            const jsonData = {
                "userId" : userId,
                "userHp" : userHp
            }
            fetch("/lotteon/my/updateHp", {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(jsonData)
            })
                .then(response => {
                    if (response.ok) {
                        hp1.readOnly = true;
                        hp2.readOnly = true;
                        hp3.readOnly = true;
                        hp1.style.border = "0";
                        hp2.style.border = "0";
                        hp3.style.border = "0";
                        btnChangeHp.classList.add('change');
                        btnChangeHp.classList.remove('save');
                    }
                    return response.json();
                })
                .then(data => {
                })
                .catch(err => console.log(err))
        }
    }

    // 이메일 수정 //
    const btnChangeEmail = document.getElementById('btnChangeEmail');
    const inputEmailCode = document.getElementById('inputEmailCode');
    const checkCodeLabel = document.getElementById('checkCodeLabel');
    const resultEmailCode = document.getElementById('resultEmailCode');
    const checkEmailCode = document.getElementById('checkEmailCode');
    const emailCodeSection = document.getElementById('emailCodeSection');
    const resultEmail = document.getElementById('resultEmail');
    const email1 = document.getElementsByName('email1')[0];
    const email2 = document.getElementsByName('email2')[0];

    btnChangeEmail.onclick = function () {

        // input태그 활성화
        if (btnChangeEmail.className === 'change') {

        // 이메일 수정 가능하게 하는 로직
            //email1.value = "";
            //email2.value = "";
            email1.style.border = "1px solid #999";
            email2.style.border = "1px solid #999";
            email1.readOnly = false;
            email2.readOnly = false;
            btnChangeEmail.classList.remove('change');
            btnChangeEmail.classList.add('checkCode');
            btnChangeEmail.innerText = "인증번호받기";

        }else if (btnChangeEmail.className === 'checkCode'){
        // 새로 작성한 이메일 인증 코드 받는 로직
            emailCodeSection.style.display = "block";
            let saveEmail1 = email1.value;
            let saveEmail2 = email2.value;
            const value = saveEmail1 + "@" + saveEmail2;
            console.log(value);
            // fetch 로 인증코드 받는 로직
            fetch(`/lotteon/member/findIdEmailCheck/${value}`)
                .then(response => response.json())
                .then(data => {
                    if (data.result === 0) {
                        // 이메일이 존재하는 경우
                        resultEmail.innerText = "인증코드가 전송되었습니다.";
                        resultEmail.style.color = "green";
                        inputEmailCode.style.border = "1px solid green";
                    } else {
                        // 이메일이 존재하지 않는 경우
                        resultEmail.innerText = "존재하지않는 이메일입니다.";
                        resultEmail.style.color = "red";
                        inputEmailCode.style.border = "1px solid red";
                    }
                })
                .catch(error => {
                    console.log('Error', error);
                });
            checkEmailCode.onclick = function (e){
                e.preventDefault();

                const inputCode = inputEmailCode.value;
                console.log(inputCode);

                fetch(`/lotteon/member/checkEmailCode/${inputCode}`)
                    .then(response => response.json())
                    .then(data => {
                        console.log(data);
                        if (data.result > 0) {
                            resultEmailCode.innerText = "인증번호가 일치하지 않습니다.";
                            resultEmailCode.style.color = "red";
                            inputEmailCode.style.border = "1px solid red";
                        } else {
                            resultEmailCode.innerText = "인증번호가 인증되었습니다.";
                            resultEmailCode.style.color = "green";
                            inputEmailCode.style.border = "1px solid green";

                            btnChangeEmail.classList.remove('checkCode');
                            btnChangeEmail.classList.add('save');
                            btnChangeEmail.innerText = "저장하기";
                        }
                    })
                    .catch(err => console.log(err))
            }
        }else if (btnChangeEmail.className === 'save') {
        // 이메일 인증 이후 수정 이메일 저장 하는 로직
            let saveEmail1 = email1.value;
            let saveEmail2 = email2.value;
            let userEmail = saveEmail1 + "@" + saveEmail2;
            console.log(userEmail);
            const userId = document.getElementById('userId').innerText;
            const jsonData = {
                "userId" : userId,
                "userEmail" : userEmail
            }
            fetch("/lotteon/my/updateEmail", {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(jsonData)
            })
                .then(response => {
                    if (response.ok) {
                        email1.readOnly = true;
                        email2.readOnly = true;
                        email1.style.border = "0";
                        email2.style.border = "0";
                        btnChangeEmail.classList.add('change');
                        btnChangeEmail.classList.remove('save');
                        btnChangeEmail.innerText = "수정하기";

                    }
                    return response.json();
                })
                .then(data => {
                })
                .catch(err => console.log(err))
        }
    }
    // 유효성 검사
    let isPassOk  = false;
    const rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;

    // 비밀번호 수정 //
    const btnChangePass = document.getElementById('btnChangePass');
    const changePw = document.getElementById('changePw');
    const resultPw = document.getElementById('resultPw');

    // 비밀번호 유효성 체크
    changePw.addEventListener('blur', function (){
        const value = changePw.value;
        const type = "userPw";
        //1. 정규 표현식 통과
        if (!value.match(rePass)) {
            resultPw.innerText = "유효하지 않은 패스워드입니다.";
            resultPw.style.color = "red";
            changePw.style.border = "1px solid red";
            isPassOk = false;
            return; // 여기서 끝!
        } else {
            resultPw.innerText = "사용가능한 비밀번호입니다.";
            resultPw.style.color = "green";
            changePw.style.border = "1px solid green";
            isPassOk = true;
            return; // 여기서 끝!
        }
    });

    btnChangePass.onclick = function (){
        // input태그 활성화
        if (btnChangePass.className === 'change') {
            changePw.style.display = "inline-block";
            btnChangePass.classList.remove('change');
            btnChangePass.classList.add('save');

        } else if (btnChangePass.className === 'save') {
            // 수정 비밀번호 저장
            let savePw = changePw.value;
            console.log(savePw);
            const userId = document.getElementById('userId').innerText;
            const jsonData = {
                "userId" : userId,
                "userPw" : savePw
            }
            fetch("/lotteon/my/updatePw", {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(jsonData)
            })
                .then(response => {
                    if (response.ok) {
                        changePw.style.display = "none";
                        btnChangePass.classList.add('change');
                        btnChangePass.classList.remove('save');
                        alert("비밀번호 수정이 완료되었습니다.");
                    }
                    return response.json();
                })
                .then(data => {
                })
                .catch(err => console.log(err))
        }
    }

    const changeAddr= document.getElementById('changeAddr');
    const doneChangeAddr= document.getElementById('doneChangeAddr');
    const userZip= document.getElementById('userZip');
    const userAddr1= document.getElementById('userAddr1');
    const userAddr2= document.getElementById('userAddr2');
    const userIdValue = document.getElementById('userId');

    //다음 주소 열기
    const btnZip = document.getElementById('btnZip');

    changeAddr.onclick = function (e){
        btnZip.style.display = "block";
        changeAddr.style.display = "none";
        doneChangeAddr.style.display = "block";

        userZip.style.border = "1px solid #999";
        userAddr1.style.border = "1px solid #999";
        userAddr2.style.border = "1px solid #999";
        userZip.readOnly = false;
        userAddr1.readOnly = false;
        userAddr2.readOnly = false;
        userZip.value = "";
        userAddr1.value = "";
        userAddr2.value = "";
    }
    btnZip.onclick = function (e) {
        e.preventDefault();
        postcode();
    }
    doneChangeAddr.onclick = function (){
        const zip = userZip.value;
        const addr1 = userAddr1.value;
        const addr2 = userAddr2.value;
        const userId = userIdValue.innerText;

        const jsonData = {
            "userZip" : zip,
            "userId" : userId,
            "userAddr1" : addr1,
            "userAddr2" : addr2
        }

        fetch("/lotteon/my/updateAddr", {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(jsonData)
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if (data != null) {
                    doneChangeAddr.style.display = "none";
                    changeAddr.style.display = "block";
                    btnZip.style.display = "none";
                    userZip.style.border = "0";
                    userAddr1.style.border = "0";
                    userAddr2.style.border = "0";
                    userZip.readOnly = true;
                    userAddr1.readOnly = true;
                    userAddr2.readOnly = true;
                }else {
                    alert("수정에 실패했습니다.")
                }

            })
            .catch(err => console.log(err))
    }
    const leave = document.getElementById('btnWithdraw');
    const userRole = document.getElementById('userRole');
    leave.onclick = function (){
        const userId = userIdValue.innerText;

        let result = confirm("정말 탈퇴하시겠습니까?");

        if(result) {
            const jsonData = {
                "userId" : userId,
                "userRole" : "DELETE"
            }
            fetch("/lotteon/my/updateRole",{
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(jsonData)
            })
                .then(response => response.json())
                .then(data =>{
                    console.log(data);
                    alert("회원탈퇴가 완료되었습니다.")
                })
                .catch(err => console.log(err))
        }else{

        }
    }
}
