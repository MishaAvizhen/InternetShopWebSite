<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta firstName="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<title>Объявление</title>

	<spring:url var="home" value="/" scope="request"/>

	<spring:url value="/resources/css/main.css" var="coreCss"/>
	<spring:url value="/resources/css/bootstrap.css" var="bootstrapCss"/>
	<spring:url value="/resources/css/font-awesome.css" var="fontAwesomeCss"/>

	<link href="${bootstrapCss}" rel="stylesheet"/>
	<link href="${coreCss}" rel="stylesheet"/>
	<link href="${fontAwesomeCss}" rel="stylesheet"/>
	<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="/resources/js/lib/bootstrap.js"></script>

	<spring:url value="/resources/js/lib/jquery.1.10.2.min.js"
				var="jqueryJs"/>
	<script src="${jqueryJs}"></script>

	<spring:url value="/resources/js/lib/jquery.i18n.properties-min-1.0.9.js" var="jqueryi18n"/>
	<script src="${jqueryi18n}"></script>


	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>
<body>
<div class="container">
	<div class="row">
		<h1>MusicShop</h1>
		<div class="navbar navbar-inverse">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#responsive-menu">
						<span class="sr-only">Open navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="/"><img src="/resources/images/companyLogo.png"></a>
				</div>
				<div class="collapse navbar-collapse" id="responsive-menu">
					<ul class="nav navbar-nav">
						<li><a href="/">Главная</a> </li>
						<li><a href="/catalog">Каталог</a></li>
						<li><a href="/contacts">Контакты</a></li>
						<li><a href="/pay_and_deliver">Оплата и доставка</a></li>
						<li><a href="/guarantee">Гарантия</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
                        <c:if test="${currentUser.id == 0}">
                            <li>
                                <a href="/login">
                                    <span class="glyphicon glyphicon-log-in"></span>
                                    Войти
                                </a>
                            </li>
                        </c:if>
                        <c:if test="${currentUser.id != 0}">
                            <c:if test="${currentUser.login != null}">
                                <li id="user-name-label">
                                    <a href="/login">
                                        <span class="glyphicon glyphicon-user"></span>
                                            ${currentUser.login}
                                    </a>
                                </li>
								<li><a href="/cart">Корзина<span class="badge">${cart.size()}</span></a></li>
                            </c:if>

                            <li>
                                <form action="/logout" method="post" class="navbar-form">
                                    <button type="submit" class="btn btn-link navbar-btn">
                                        <span class="glyphicon glyphicon-log-out"></span>
                                        Выйти
                                    </button>
                                </form>
                            </li>
                        </c:if>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="container">
	<div class="row">
		<h3>Объявление</h3>
	</div>
</div>

<div class="container">
	<div class="row">
		<div class="col-md-10 col-lg-10" id="contentContainer">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-7 col-md-7">
							<c:if test="${isEditMode}">
								<form action="/admin/editAdvert" method="post" id="editForm">
									<input type="hidden" name="advertId" id="advertId" value="${advert.id}">
									<div class="form-group">
										<fieldset>
											<label class="control-label" for="advertName">
												Название
											</label>
											<input class="form-control" name="advertName" id="advertName" type="text" required="required" value="${advert.name}"/>

											<label class="control-label" for="advertGroup">
												Группа инструмента
											</label>
											<input class="form-control" name="advertGroup" id="advertGroup" type="text" required="required" value="${advert.groupName}"/>

											<label class="control-label" for="advertPrice">
												Цена
											</label>
											<input class="form-control" name="advertPrice" id="advertPrice" type="text" required="required" value="${advert.price}"/>

											<label class="control-label" for="advertCountry">
												Страна производитель
											</label>
											<input class="form-control" name="advertCountry" id="advertCountry" type="text" required="required" value="${advert.country}"/>

											<label class="control-label" for="advertGuarantee">
												Гарантия
											</label>
											<input class="form-control" name="advertGuarantee" id="advertGuarantee" type="text" required="required" value="${advert.guaranteeAmountOfMonth}"/>

											<label class="control-label" for="advertDescription">
												Описание объявления
											</label>
											<input class="form-control" name="advertDescription" id="advertDescription" type="text" value="${advert.description}"/>
										</fieldset>
									</div>

									<input type="submit" class="btn btn-primary" value="Сохранить">
								</form>
							</c:if>
							<c:if test="${not isEditMode}">
								<strong>Название:</strong> ${advert.name}<br>
								<strong>Группа инструментов:</strong> ${advert.groupName}<br>
								<strong>Цена:</strong> ${advert.price} BYN <br>
								<strong>Страна производитель:</strong> ${advert.country}<br>
								<strong>Гарантия(в месяцах):</strong> ${advert.guaranteeAmountOfMonth}<br>
								<strong>Описание:</strong> ${advert.description}<br>
								<hr>

								<c:if test="${currentUser.id == 0}">
									<strong>Чтобы добавить в корзину, пожалуйста, войдите в систему</strong>
								</c:if>
								<c:if test="${currentUser.id != 0}">
									<c:if test="${not empty addToCartMsg}">
										<div class="msg">${addToCartMsg}</div>
									</c:if>
									<a class="btn btn-primary" href="/addToCart/${advert.id}" role="button">Добавить в корзину</a>
								</c:if>
							</c:if>
						</div>
						<div class="col-lg-5 col-md-5">
							<img height="300" width="400" src="/resources/images/${advert.imageName}">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-2 col-lg-2" id="rightBarContainer">
			<c:if test="${currentUser.isAdmin()}">
				<a class="btn btn-primary" href="/editAdvert/${advert.id}" role="button">Изменить</a>
				<a class="btn btn-danger" href="/removeAdvert/${advert.id}" role="button">Удалить</a>
			</c:if>
		</div>
	</div>
</div>

<div class="container" id="footer">
	<hr/>
	<div class="text-center center-block">
		<p class="txt-railway">Путь к сайту</p>
		<br />
		<a href="https://vk.com/lizonatrishka"><i class="fa fa fa-vk fa-3x social"></i></a>
		<a href="mailto:liza.lagunovskaya@gmail.com"><i class="fa fa-envelope-square fa-3x social"></i></a>
	</div>
	<hr />
</div>

</body>
</html>