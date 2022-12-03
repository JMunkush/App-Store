
function addDelClass(el, className) {
	if (el.classList.contains(`${className}_active`)) {
		el.classList.remove(`${className}_active`)
	} else {
		el.classList.add(`${className}_active`)
	}
}

function searchOpen() {

	const searchIcon = document.querySelector('#searchIcon')
	const searchInp = document.querySelector('#searchInp')

	searchIcon.addEventListener('click', () => {
		addDelClass(searchInp, 'head-top__search-inp')
	})

}

function menuOpen() {

	const menuIcon = document.querySelector('#menuIcon')
	const headNavBttm = document.querySelector('.head-nav-bttm')

	if (!menuIcon) {
		return
	}

	menuIcon.addEventListener('click', () => {
		addDelClass(headNavBttm, 'head-nav-bttm')
	})

}

function blogSwiperInit() {
	if (!Swiper) {
		return
	}

	const swiper = new Swiper('.blog__swiper', {

		loop: true,
		navigation: {
			nextEl: '.blog__btn_next',
			prevEl: '.blog__btn_prev',
		},
	});
}

function homeSwiperInit() {
	if (!Swiper) {
		return
	}

	const swiper = new Swiper('.promo-slider', {

		loop: true,
		navigation: {
			nextEl: '.swiper-button-next',
			prevEl: '.swiper-button-prev',
		},
	});
}

function abAuthorSwiperInit() {
	if (!Swiper) {
		return
	}

	const swiper = new Swiper('.about-author__slider', {

		loop: true,
		spaceBetween: 10,
		slidesPerView: 3,
		navigation: {
			nextEl: '.swiper-button-next',
			prevEl: '.swiper-button-prev',
		},
		breakpoints: {
			// when window width is >= 320px
			620: {
				slidesPerView: 4,
			},
			768: {
				slidesPerView: 5,
			},
		}
	});
}

function collabSwiperInit() {
	if (!Swiper) {
		return
	}

	const swiper = new Swiper('.collab__row', {

		loop: true,
		spaceBetween: 10,
		slidesPerView: 3,
		navigation: {
			nextEl: '.swiper-button-next',
			prevEl: '.swiper-button-prev',
		},
		breakpoints: {
			// when window width is >= 320px
			620: {
				slidesPerView: 4,
			},
			1040: {
				slidesPerView: 4,
				spaceBetween: 24,
			},
		}
	});
}

function itemSwiperInit() {
	if (!Swiper) {
		return
	}

	const swiper = new Swiper('.item__slider-top', {
		loop: true,
		spaceBetween: 10,
		slidesPerView: 1,
		navigation: {
			nextEl: '.swiper-button-next',
			prevEl: '.swiper-button-prev',
		},
	});
}

function counterItem() {

	let counterWrap = document.querySelector('.item__info-counter')
	let numberCounterSpan = document.querySelector('.item__info-counter-num')
	let counterNum = 1

	if (!counterWrap) {
		return
	}

	counterWrap.addEventListener('click', (ev) => {
		if (ev.target.classList.contains('item__info-counter-btn_increment')) {
			if (counterNum <= 1) {
				return
			}
			counterNum--
		}
		else if (ev.target.classList.contains('item__info-counter-btn_decrement')) {
			counterNum++
		}
		else {
			return
		}

		numberCounterSpan.textContent = counterNum
	})



}

function supportModalTxtInit() {
	const supportModalSubmitBtn = document.querySelector('.modal-card__submit-btn')
	const supportModalTxt = document.querySelector('.support-modal-txt')
	const bodyEl = document.querySelector('body')

	supportModalSubmitBtn.addEventListener('click', () => {
		addDelClass(supportModalTxt, 'support-modal-txt')
		addDelClass(bodyEl, 'body-hide')

		setTimeout(() => {
			window.location = './'
			addDelClass(bodyEl, 'body-hide')
		}, 3000);
	})
}

function colorChangeItemInit() {

	const imgUrlsList = [
		{
			itemColor: 'Желтый',
			imgUrls: ['img/item1.jpg', 'img/item2.jpg', 'img/item3.jpg', 'img/item4.jpg', 'img/item5.jpg']
		},
		{
			itemColor: 'Черный',
			imgUrls: ['img/product1.jpg', 'img/product2.jpg', 'img/product3.jpg', 'img/product4.jpg', 'img/product1.jpg']
		},
		{
			itemColor: 'Красный',
			imgUrls: ['img/tshirt1.jpg', 'img/tshirt2.jpg', 'img/tshirt3.jpg', 'img/tshirt4.jpg', 'img/tshirt1.jpg']
		},
	]

	const itemColorsList = document.querySelector('#itemColorsList')
	const itemImgsList = document.querySelectorAll('.item__images-img')

	if (!itemColorsList && itemImgsList) {
		return
	}

	itemColorsList.addEventListener('click', ev => {
		const colorValue = ev.target.dataset.color

		imgUrlsList.forEach(i => {
			if (i.itemColor === colorValue) {
				itemImgsList.forEach((k, idx) => {
					k.firstElementChild.src = i.imgUrls[idx]
				})
			}
		})
	})
}

document.addEventListener('DOMContentLoaded', () => {
	menuOpen()
	searchOpen()
	homeSwiperInit()
	blogSwiperInit()
	counterItem()
	supportModalTxtInit()
	// colorChangeItemInit()
	abAuthorSwiperInit()
	collabSwiperInit()
	itemSwiperInit()
})
