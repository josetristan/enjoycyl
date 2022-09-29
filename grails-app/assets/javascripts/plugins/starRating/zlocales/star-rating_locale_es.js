/*!
 * Star Rating Spanish Translations
 *
 * This file must be loaded after 'star-rating.js'. Patterns in braces '{}', or
 * any HTML markup tags in the messages must not be converted or translated.
 *
 * NOTE: this file must be saved in UTF-8 encoding.
 *
 * bootstrap-star-rating v4.1.3
 * http://plugins.krajee.com/star-rating
 *
 * Copyright: 2013 - 2021, Kartik Visweswaran, Krajee.com
 *
 * Licensed under the BSD 3-Clause
 * https://github.com/kartik-v/bootstrap-star-rating/blob/master/LICENSE.md
 */
(function (factory) {
    'use strict';
    if (typeof define === 'function' && define.amd) {
        define(['jquery'], factory);
    } else if (typeof module === 'object' && typeof module.exports === 'object') { 
        factory(require('jquery'));
    } else { 
        factory(window.jQuery);
    }
}(function ($) {
    "use strict";
    $.fn.ratingLocales.es = {
        defaultCaption: '{rating} Estrellas',
        starCaptions: {
            0.5: 'Mal',
            1: 'Mal',
            1.5: 'Mal',
            2: 'No me gusta',
            2.5: 'No me gusta',
            3: 'Bien',
            3.5: 'Bien',
            4: 'Bastante bien',
            4.5: 'Bastante bien',
            5: 'Genial'
        },
        clearButtonTitle: 'Limpiar',
        clearCaption: 'Sin Calificar'
    };
}));