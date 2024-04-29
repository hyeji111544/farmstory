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
    const email1 = document.getElementsByName('email1')[0];
    const email2 = document.getElementsByName('email2')[0];

    btnChangeEmail.onclick = function (){
        // input태그 활성화
        if (btnChangeEmail.className === 'change') {
            email1.value = "";
            email2.value = "";
            email1.style.border = "1px solid #999";
            email2.style.border = "1px solid #999";
            email1.readOnly = false;
            email2.readOnly = false;
            btnChangeEmail.classList.remove('change');
            btnChangeEmail.classList.add('save');

        } else if (btnChangeEmail.className === 'save') {
            // 수정 이메일 저장
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
                    }
                    return response.json();
                })
                .then(data => {
                })
                .catch(err => console.log(err))
        }
    }

    // 비밀번호 수정 //
    const btnChangePass = document.getElementById('btnChangePass');
    const changePw = document.getElementById('changePw');

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
                    }
                    return response.json();
                })
                .then(data => {
                })
                .catch(err => console.log(err))
        }
    }
}
