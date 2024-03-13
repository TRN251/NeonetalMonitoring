var frameUtils = {
    toDataURL: function(mat) {
        var img = new Image();
        var matBytes = new Uint8Array(mat.total() * mat.elemSize());
        mat.copyTo(matBytes);
        var blob = new Blob([matBytes], {type: 'image/jpeg'});
        var reader = new FileReader();
        reader.onload = function(e) {
            img.src = e.target.result;
        };
        reader.readAsDataURL(blob);
        return img.src;
    }
};
