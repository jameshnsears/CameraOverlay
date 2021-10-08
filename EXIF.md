# Viewing / Adding exif GPS coordinates (decimal)

## 1. Tower_Bridge_from_Shad_Thames.jpg

* https://en.wikipedia.org/wiki/Tower_Bridge
* https://geohack.toolforge.org/geohack.php?pagename=Tower_Bridge&params=51.5055_N_0.075406_W_

```text
51.5055, -0.075406

exiftool -gpslatitude=51.5055 -gpslongitude=-0.075406 Tower_Bridge_from_Shad_Thames.jpg
exiftool -gpslatituderef=N -gpslongituderef=W Tower_Bridge_from_Shad_Thames.jpg

exiftool -gpslatitude -gpslongitude Tower_Bridge_from_Shad_Thames.jpg
GPS Latitude                    : 51 deg 30' 19.80" N
GPS Longitude                   : 0 deg 4' 31.46" W
```

## 2. Berlin_reichstag_west_panorama_2.jpg

* https://en.wikipedia.org/wiki/Reichstag_building
* https://geohack.toolforge.org/geohack.php?pagename=Reichstag_building&params=52.5186_N_13.3763_E_region:DE-BE_type:landmark

```text
52.5186, 13.3763

exiftool -gpslatitude=52.5186 -gpslongitude=13.3763 Berlin_reichstag_west_panorama_2.jpg
exiftool -gpslatituderef=N -gpslongituderef=E Berlin_reichstag_west_panorama_2.jpg
```
