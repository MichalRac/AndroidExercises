package com.example.androidmodelviewer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class AndroidModelViewer extends ApplicationAdapter {
	private final int WIDTH = 480;
	private final int HEIGHT = 800;
	private Texture leftArrow;
	private Texture rightArrow;

	private OrthographicCamera spriteCamera;
	private List<Texture> images;
	private SpriteBatch spriteBatch;
	private Rectangle imageContainer;

	private PerspectiveCamera modelCamera;
	private CameraInputController cameraController;
	private List<Model> models;
	private ModelBatch modelBatch;
	Environment environment;
	ModelInstance currentModelInstance;

	private boolean mode3D = false;
	private int currentImage = 0;
	private int currentModel = 0;

	public AssetManager assets;
	public boolean loading;
	public Array<ModelInstance> instances = new Array<ModelInstance>();

	@Override
	public void create () {
		leftArrow = new Texture(Gdx.files.internal("arrow-left.png"));
		rightArrow = new Texture(Gdx.files.internal("arrow-right.png"));

		/// 2D
		images = new ArrayList<Texture>();
		images.add(new Texture(Gdx.files.internal("mmm_2d_example_1.png")));
		images.add(new Texture(Gdx.files.internal("mmm_2d_example_2.png")));
		images.add(new Texture(Gdx.files.internal("bucket.png")));
		images.add(new Texture(Gdx.files.internal("drop.png")));

		imageContainer = new Rectangle();
		imageContainer.x = WIDTH / 2 - images.get(currentImage).getWidth() / 2;
		imageContainer.y = HEIGHT / 2 - images.get(currentImage).getHeight() / 2;
		imageContainer.width = 128;
		imageContainer.height = 128;

		spriteCamera = new OrthographicCamera();
		spriteCamera.setToOrtho(false, WIDTH, HEIGHT);
		spriteBatch = new SpriteBatch();

		/// 3D
/*
		models = new ArrayList<Model>();
		ModelLoader loader = new ObjLoader();
		models.add(loader.loadModel(Gdx.files.internal("3D_complex_MMM.obj")));
		//models.add(new G3dModelLoader(new JsonReader()).loadModel(Gdx.files.internal("3D_complex_MMM.obj")));

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		modelBatch = new ModelBatch();

		ModelBuilder modelBuilder = new ModelBuilder();
		Model model = models.get(currentModel);
		currentModelInstance = new ModelInstance(model);

		modelCamera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		modelCamera.position.set(1f, 1f, 1f);
		modelCamera.lookAt(0,0,0);
		modelCamera.near = 1f;
		modelCamera.far = 300f;
		modelCamera.update();

		cameraController = new CameraInputController(modelCamera);
		Gdx.input.setInputProcessor(cameraController);
*/
		Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
		assets = new AssetManager();
		assets.load("3D_weird_smooth.obj", Model.class);
		assets.load("3D_complex_MMM.obj", Model.class);
		assets.load("3D_brick_cube.obj", Model.class);
		assets.load("3Dconverted_complex_MMM.g3db", Model.class);
		assets.load("2D_simple.obj", Model.class);
		assets.load("2D_textured.obj", Model.class);
		assets.load("2D_mirror_hexagon.obj", Model.class);

		loading = true;

		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.9f, 0.9f, 0.9f, 1f));
		environment.add(new DirectionalLight().set(0.6f, 0.6f, 0.6f, -1f, -0.8f, -0.2f));

		modelCamera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		modelCamera.position.set(10f, 10f, 10f);
		modelCamera.lookAt(0,0,0);
		modelCamera.near = 1f;
		modelCamera.far = 300f;
		modelCamera.update();

		cameraController = new CameraInputController(modelCamera);
		Gdx.input.setInputProcessor(cameraController);

	}

	@Override
	public void render () {
		//ScreenUtils.clear(1, 1, 1, 1);

		if(mode3D)
		{
			if (loading && assets.update())
			{
				doneLoading();
			}
			else
			{
				if(!loading)
				{
					cameraController.update();

					Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
					Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

					modelBatch.begin(modelCamera);
					modelBatch.render(instances.get(currentModel), environment);
					modelBatch.end();

					spriteBatch.begin();
					{
						spriteBatch.draw(rightArrow, WIDTH - rightArrow.getWidth(), 0);
						spriteBatch.draw(leftArrow, 0, 0);
					}
					spriteBatch.end();
				}
			}
		}
		else
		{
			ScreenUtils.clear(0.9f, 0.9f, 0.9f, 1);

			spriteCamera.update();
			spriteBatch.setProjectionMatrix(spriteCamera.combined);

			spriteBatch.begin();
			{
				spriteBatch.draw(rightArrow, WIDTH - rightArrow.getWidth(), 0);
				spriteBatch.draw(leftArrow, 0, 0);
				spriteBatch.draw(images.get(currentImage), imageContainer.x, imageContainer.y);
			}
			spriteBatch.end();
		}

		ProcessModelAction();
		ProcessMenuActions();
	}
	private void doneLoading() {
		List<Model> loadedModels = new ArrayList<Model>();
		loadedModels.add(assets.get("3D_weird_smooth.obj", Model.class));
		loadedModels.add(assets.get("3D_complex_MMM.obj", Model.class));
		loadedModels.add(assets.get("3D_brick_cube.obj", Model.class));
		loadedModels.add(assets.get("3Dconverted_complex_MMM.g3db", Model.class));
		loadedModels.add(assets.get("2D_simple.obj", Model.class));
		loadedModels.add(assets.get("2D_textured.obj", Model.class));
		loadedModels.add(assets.get("2D_mirror_hexagon.obj", Model.class));

		for (Model loadedModel: loadedModels)
		{
			ModelInstance modelInstance = new ModelInstance(loadedModel);
			modelInstance.transform.scale(0.01f, 0.01f, 0.01f);
			instances.add(modelInstance);
		}

		loading = false;
	}

	public void ProcessModelAction()
	{
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			spriteCamera.unproject(touchPos);

			if (touchPos.y > 64 + images.get(currentImage).getHeight() / 2) {
				imageContainer.x = touchPos.x - images.get(currentImage).getWidth() / 2;
				imageContainer.y = touchPos.y - images.get(currentImage).getHeight() / 2;
			}
		}
	}

	public void ProcessMenuActions()
	{
		if(Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			spriteCamera.unproject(touchPos);

			if(touchPos.y < 64 + images.get(currentImage).getHeight() / 2)
			{
				if(touchPos.x < 64)
				{
					if(mode3D)
					{
						if(currentModel == 0)
						{
							mode3D = false;
							currentImage = 3;
						}
						else
						{
							currentModel--;
						}

					}
					else
					{
						if(currentImage == 0)
						{
							mode3D = true;
							currentModel = 6;
						}
						else
						{
							currentImage--;
						}
					}
				}
				else if(touchPos.x > WIDTH - 64)
				{
					if(mode3D)
					{
						if(currentModel == instances.size - 1)
						{
							mode3D = false;
							currentImage = 0;
						}
						else
						{
							currentModel++;
						}
					}
					else
					{
						if(currentImage == images.size() - 1)
						{
							mode3D = true;
							currentModel = 0;
						}
						else
						{
							currentImage++;
						}
					}
				}

				imageContainer.x = WIDTH / 2 - images.get(currentImage).getWidth() / 2;
				imageContainer.y = HEIGHT / 2 - images.get(currentImage).getHeight() / 2;
			}
		}
	}

	@Override
	public void dispose () {
		spriteBatch.dispose();
		modelBatch.dispose();
		instances.clear();
		assets.dispose();
	}
}
